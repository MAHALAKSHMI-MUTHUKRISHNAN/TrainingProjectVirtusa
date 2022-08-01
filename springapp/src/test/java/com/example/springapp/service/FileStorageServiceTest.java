package com.example.springapp.service;

import com.example.springapp.dao.FileDBDao;
import com.example.springapp.entity.FileDB;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class FileStorageServiceTest {

    @Mock
    private FileDBDao fileDBDao;

    @InjectMocks
    private FileStorageService fileStorageService;

    @Test
    public void testStoreFile() throws IOException {

    }

}
