package com.example.demo.entities.general;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(TopicId.class)
public class Topic  {

    @Id
    private String name;
    @Id
    private String namedEntityRecognitionType;

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Topic))
            return false;
        Topic other = (Topic)o;
        boolean currencyCodeEquals = (this.name == null && other.name == null)
                || (this.name != null && this.name.equals(other.name));
        return Objects.equals(this.namedEntityRecognitionType, other.namedEntityRecognitionType) && currencyCodeEquals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, namedEntityRecognitionType);
    }

}

