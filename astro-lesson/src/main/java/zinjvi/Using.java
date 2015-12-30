package zinjvi;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "public.using")
public class Using {
    @Id
    @SequenceGenerator(name = "USING_SEQ", sequenceName = "using_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "USING_SEQ", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name="idBook", referencedColumnName="idBook")
    private Book idBook;

    @ManyToOne
    @JoinColumn(name="idReader", referencedColumnName="idReader")
    private Reader idReader;
    private Date dateGotten;
    private Date dateMustReturn;
    private Boolean complete;
}

