package com.mcgill.mymuseum.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.mcgill.mymuseum.model.*;
import com.mcgill.mymuseum.repository.*;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.html.Option;
import java.sql.Date;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTestSchedule {

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    WorkDayRepository workDayRepository;

    @InjectMocks
    EmployeeService employeeService;


    @Nested
    @DisplayName("test get schedule method")
    class GetSchedule {
        @Test
        void listIsNull() {
            long id = 21;
            Mockito.when(workDayRepository.findWorkDaysByEmployee_AccountId(id)).thenReturn(Collections.emptyList());
            assertEquals(Collections.emptyList(), employeeService.getSchedule(id));
        }

        @Test
        void listIsFull() {
            long id = 21;
            List<WorkDay> listOfWorkDays = new ArrayList<WorkDay>();
            Mockito.when(workDayRepository.findWorkDaysByEmployee_AccountId(id)).thenReturn(listOfWorkDays);
            assertEquals(listOfWorkDays, workDayRepository.findWorkDaysByEmployee_AccountId(id));
        }
    }
    @Nested
    @DisplayName("test get workDay by Date")
    class GetWorkDayByDate {
        @Test
        void workDayDoesntExist() {
            WorkDay workDay = new WorkDay();
            long id = 21;
            Date day = Date.valueOf("2013-09-04");
            workDay.setDay(day);
            Mockito.when(workDayRepository.findWorkDayByDayAndEmployeeAccountId(day,id)).thenReturn(null);
            assertEquals(employeeService.getWorkDayByDate(day, id), null);
        }

        @Test
        void workDayExists() {
            WorkDay workDay = new WorkDay();
            long id = 21;
            Date day = Date.valueOf("2013-09-04");
            workDay.setDay(day);
            Mockito.when(workDayRepository.findWorkDayByDayAndEmployeeAccountId(day,id)).thenReturn(workDay);
            assertEquals(employeeService.getWorkDayByDate(day,id), workDay);

        }
    }
    @Nested
    @DisplayName("test delete work day")
    class DeleteWorkDay {
        @Test
        void workDayDoesntExist() {
            WorkDay workDay = new WorkDay();
            long id = 21;
            Date day = Date.valueOf("2013-09-04");
            workDay.setDay(day);
            Mockito.when(workDayRepository.deleteWorkDayByDayAndEmployeeAccountId(day,id)).thenReturn(null);
            assertEquals(employeeService.deleteWorkDay(day, id), null);
        }

        @Test
        void workDayExists() {
            WorkDay workDay = new WorkDay();
            long id = 21;
            Date day = Date.valueOf("2013-09-04");
            workDay.setDay(day);
            Mockito.when(workDayRepository.deleteWorkDayByDayAndEmployeeAccountId(day,id)).thenReturn(workDay);
            assertEquals(employeeService.deleteWorkDay(day,id), workDay);
        }
    }
    @Nested
    @DisplayName("test save work day")
    class saveWorkDay {
        @Test
        void workDayIsNull() {
            Mockito.when(workDayRepository.save(null)).thenReturn(null);
            assertEquals(employeeService.saveWorkDay(null), null);
        }

        @Test
        void workDayExists() {
            WorkDay workDay = new WorkDay();
            Mockito.when(workDayRepository.save(workDay)).thenReturn(workDay);
            assertEquals(employeeService.saveWorkDay(workDay), workDay);
        }
    }

}
