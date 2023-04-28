package com.sedlacek.quiz.entity;

import lombok.Getter;
import org.modelmapper.ModelMapper;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@Getter
public abstract class EntityBase implements Serializable {
    private static final ModelMapper mapper = new ModelMapper();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public static <T, E> E convert(T source, Class<E> destinationClass) {
        return mapper.map(source, destinationClass);
    }

}
