package com.example.demo.entities.general;

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
public class TopicId implements Serializable {

    private String name;
    private String namedEntityRecognitionType;

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TopicId))
            return false;
        TopicId other = (TopicId)o;
        boolean currencyCodeEquals = (this.name == null && other.name == null)
                || (this.name != null && this.name.equals(other.name));
        return Objects.equals(this.namedEntityRecognitionType, other.namedEntityRecognitionType) && currencyCodeEquals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, namedEntityRecognitionType);
    }
}
