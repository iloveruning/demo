package com.cll.demo.example;

import com.cll.demo.example.dto.RecordQuery;
import com.cll.demo.example.entity.Record;
import com.cll.demo.example.enums.StateEnum;
import com.cll.demo.example.service.RecordService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


public class ExampleApplicationTests extends BaseMvcTest {


    @Test
    public void test1() {
        RecordService recordService = context.getBean(RecordService.class);
        Record record = new Record();
        record.setClassId(123456789L);
        record.setHonourId(123456789L);
        record.setState(StateEnum.NEWEST);
        record.setStartDate(new Date());
        record.setEndDate(new Date());
        record.setUserId(123456789L);

        Assert.assertTrue(recordService.save(record));
    }


    @Test
    public void saveRecord_test() {
        RecordService recordService = context.getBean(RecordService.class);

        RecordQuery query = new RecordQuery();
        query.setClassIds(Collections.singletonList("123456789"));
        query.setHonourId("123456789");
        query.setUserId("123456789");
        query.setStartDate(strToDate("2018-10-24"));
        query.setEndDate(strToDate("2018-11-04"));

        recordService.saveRecord(query);
    }


    @Test
    public void saveRecord_thread_test() throws InterruptedException {
        RecordService recordService = getContext().getBean(RecordService.class);

        int threadNum = 10;

        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        CyclicBarrier barrier = new CyclicBarrier(threadNum);
        CountDownLatch latch=new CountDownLatch(threadNum);
        AtomicInteger count = new AtomicInteger(10);

        for (int i = 0; i < threadNum; i++) {
            executorService.execute(() -> {
                RecordQuery query = new RecordQuery();
                query.setClassIds(Collections.singletonList("123456789"));
                query.setHonourId("123456789");
                query.setUserId("123456789");
                query.setStartDate(strToDate("2018-10-24"));
                query.setEndDate(strToDate("2018-11-" + count.getAndIncrement()));

                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    //ignore
                }
                try {
                    recordService.saveRecord(query);
                }finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();
    }


    private Date strToDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }


}
