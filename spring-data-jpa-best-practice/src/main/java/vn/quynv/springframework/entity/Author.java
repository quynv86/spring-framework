package vn.quynv.springframework.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

@NamedEntityGraph(name="Author.books", attributeNodes = @NamedAttributeNode("books"))
@NamedEntityGraph(name="Author.publisher", attributeNodes = @NamedAttributeNode("publisher"))
public class Author {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private  String genre;
    private  String name;
    private  Integer age;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="publisher_id")
    @Fetch(FetchMode.JOIN)
    private Publisher publisher;

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", genre='" + genre + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", books=" + books +
                '}';
    }
}
