package vn.quynv.springframework.entity;

import lombok.*;

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
public class Author {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private  String genre;
    private  String name;
    private  Integer age;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books;

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
