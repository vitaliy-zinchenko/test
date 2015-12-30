package zinjvi;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "reader")
public class Reader {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long idReader;

    private String name;
    private String address;
    @OneToMany(mappedBy = "idReader")
    private Set<Using> using;

    @Override
    public String toString() {
        return "Reader{" +
                "idReader=" + idReader +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", using=" + using +
                '}';
    }

    public Long getIdReader() {
        return idReader;
    }

    public void setIdReader(Long idReader) {
        this.idReader = idReader;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Using> getUsing() {
        return using;
    }

    public void setUsing(Set<Using> using) {
        this.using = using;
    }
}
