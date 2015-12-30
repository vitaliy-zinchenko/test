package zinjvi;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Book {
    @Id
    @Column(name = "idBook")
    private Long idBook;

    @Column(name = "title")
    private String title;

    private String pre;
    @ManyToMany(targetEntity = Author.class, fetch = FetchType.LAZY)
    @JoinTable(name="authorsbooks",
            joinColumns={@JoinColumn(name="idBook")},
            inverseJoinColumns={@JoinColumn(name="idAuthor")})
    private Set authors;
    private Date whenAdded;
    private Boolean available;

    @OneToMany
    @JoinColumn(name="idBook", referencedColumnName="idBook")
    private Set<Using> using;
}

