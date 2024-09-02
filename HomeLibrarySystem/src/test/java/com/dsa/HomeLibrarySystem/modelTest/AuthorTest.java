package com.dsa.HomeLibrarySystem.modelTest;

import com.dsa.HomeLibrarySystem.model.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

@ExtendWith(MockitoExtension.class)
public class AuthorTest {

    @Mock
    private JdbcTemplate template;

    @InjectMocks
    private Author underTest;

//    @Test
//    public void testThatCreateAuthorGeneratesCorrectSql(){
//        Author author = Author.builder()
//                .id(2L)
//                .name("Abigail Rose")
//                .age(80)
//                .country("United States")
//                .build();
//    }
}
