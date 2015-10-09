package zinjvi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Vitaliy on 10/6/2015.
 */
@RestController
public class Controller {

    @ResponseBody
    @RequestMapping("/test")
    public String test() {
        return "ttt";
    }

}
