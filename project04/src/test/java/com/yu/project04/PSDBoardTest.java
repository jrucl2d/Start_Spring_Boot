package com.yu.project04;

import com.yu.project04.domain.PSDBoard;
import com.yu.project04.domain.PSDFile;
import com.yu.project04.repository.PSDBoardRepository;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log
@Commit // @Transactional이 기본적으로 Rollback 시도를 하므로 Commit 붙여야 됨
public class PSDBoardTest {
    @Autowired
    private PSDBoardRepository psdBoardRepository;

    @Test
    public void insertPSDTest(){
        PSDBoard psdBoard = new PSDBoard();
        psdBoard.setPname("Document");

        PSDFile file1 = new PSDFile();
        file1.setPdsfile("file1.doc");

        PSDFile file2 = new PSDFile();
        file2.setPdsfile("file2.doc");

        psdBoard.setFiles(Arrays.asList(file1, file2));

        log.info("파일을 저장...");
        psdBoardRepository.save(psdBoard);
    }

    @Test
    @Transactional // @Query를 사용해서 update나 delete할 때는 무조건 이거 붙여야 함
    public void updateFilenameTest1(){
        Long fno = 1L;
        String newName = "updatedFile1.doc";

        int count = psdBoardRepository.updatePSDFile(fno, newName);
        log.info("업데이트 개수 : " + count);
    }

    @Transactional
    @Test
    public void updateFilenameTest2(){
        String newName = "updatedFile2.doc";
        psdBoardRepository.findById(2L).ifPresent(
                psd -> {
                    log.info("데이터가 존재하므로 update 수행...");
                    PSDFile target = new PSDFile();
                    target.setFno(2L);
                    target.setPdsfile(newName);

                    int index = psd.getFiles().indexOf(target);
                    if(index > -1){
                        List<PSDFile> list = psd.getFiles();
                        list.remove(index); // fno 값으로 equals()와 hashcode()를 수행
                        list.add(target);
                        psd.setFiles(list);
                    }
                    psdBoardRepository.save(psd);
                }
        );
    }

    @Transactional
    @Test
    public void testDelete(){
        Long theIndex = 2L;
        int count = psdBoardRepository.deletePsdFile(theIndex);
        log.info("삭제한 파일 개수 : " + count);
    }

    @Test
    public void insertDummies(){
        List<PSDBoard> list = new ArrayList<>();
        IntStream.range(1, 100).forEach(i -> {
            PSDBoard psd = new PSDBoard();
            psd.setPname("자료 "+ i);
            PSDFile file1 = new PSDFile();
            file1.setPdsfile("file1.doc");

            PSDFile file2 = new PSDFile();
            file2.setPdsfile("file2.doc");

            psd.setFiles(Arrays.asList(file1, file2));

            log.info("파일을 저장...");
            list.add(psd);
        });
        psdBoardRepository.saveAll(list);
    }

    @Test
    public void viewSummary(){
        psdBoardRepository.getSummary().forEach(v ->
                log.info(Arrays.toString(v)));
    }
}
