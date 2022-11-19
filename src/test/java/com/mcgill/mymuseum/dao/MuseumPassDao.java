package com.mcgill.mymuseum.dao;
import com.mcgill.mymuseum.model.MuseumPass;
import com.mcgill.mymuseum.repository.MuseumPassRepository;
import com.mcgill.mymuseum.service.MuseumPassService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;

public class MuseumPassDao{


    @Mock
    private MuseumPassRepository passRepositoryDao;

    @InjectMocks
    private MuseumPassService service;

    private static final int PASS_ID = 1;

    @BeforeEach
    public void setMockOutput() {
        lenient().when(passRepositoryDao.findById(anyLong())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(PASS_ID)) {
                MuseumPass pass = new MuseumPass();
                pass.setPassId((long) PASS_ID);
                return pass;
            } else {
                return null;
            }
        });
    }
    }