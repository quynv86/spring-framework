package vn.quynv.springframework.webflux.repository;

import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import vn.quynv.springframework.webflux.domain.TaskDescriptionOnly;

@ReadingConverter
public class TaskDescriptionConverter implements Converter<Row, TaskDescriptionOnly> {
    @Override
    public TaskDescriptionOnly convert(Row source) {
        return TaskDescriptionOnly.builder().description(source.get("description",String.class))
                .build();
    }
}
