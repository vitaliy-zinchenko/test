package jms.camel;

import com.sun.org.glassfish.external.statistics.Statistic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.JsonLibrary;

import javax.jms.ConnectionFactory;

public class Main {

    private static void sleep() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        final CamelContext camelContext = new DefaultCamelContext();
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "admin", ActiveMQConnection.DEFAULT_BROKER_URL);
        camelContext.addComponent("activemq", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
        camelContext.addRoutes(
                new RouteBuilder() {
                    @Override
                    public void configure() throws Exception {
                        from("activemq:queue:from")
                                .pipeline()
                                    .unmarshal().json(JsonLibrary.Jackson, JsonBean.class)
                                    .process(this::extractName)
                                    .process(this::mapToStatistic)
                                    .filter(this::filter)
                                    .choice()
                                        .when(exchange -> exchange.getIn().getBody(StatisticBean.class).getSize() > 3)
                                            .marshal().json(JsonLibrary.Jackson, StatisticBean.class)
                                            .to("activemq:queue:to")
                                        .otherwise()
                                            .marshal().json(JsonLibrary.Jackson, StatisticBean.class)
                                            .to("activemq:queue:small-to");
                    }

                    public void extractName(Exchange exchange) {
                        System.out.println("start extract name");
                        JsonBean bean = exchange.getIn().getBody(JsonBean.class);
                        Main.sleep();
                        exchange.getIn().setBody(bean.getName());
                        System.out.println("finish extract name");
                    }

                    private void mapToStatistic(Exchange exchange) {
                        System.out.println("start map");
                        String name = exchange.getIn().getBody(String.class);
                        StatisticBean statisticBean = new StatisticBean();
                        statisticBean.setSize(name.length());
                        Main.sleep();
                        exchange.getIn().setBody(statisticBean);
                        System.out.println("finish map");
                    }

                    private boolean filter(Exchange exchange) {
                        System.out.println("start filter");
                        StatisticBean statisticBean = exchange.getIn().getBody(StatisticBean.class);
                        Main.sleep();
                        System.out.println("finish filter");
                        return statisticBean.getSize() > 0;
                    }

                });
        camelContext.start();
        Thread.sleep(100000000);
        camelContext.stop();
    }

}
