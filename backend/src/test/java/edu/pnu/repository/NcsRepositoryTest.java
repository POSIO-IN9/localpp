//package edu.pnu.repository;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import edu.pnu.domain.Ncs;
//import lombok.extern.log4j.Log4j2;
//
////
//@Log4j2
//public class NcsRepositoryTest {
//	@Autowired
//	private NcsRepository ncsRepository;
//
//	@Test
//	public void test() {
//		log.info("----------------------------------");
//		log.info(ncsRepository);
//	}
//
//	@Test
//	public void testRead() {
//		//pk로 확인
//		Long ncsid= 22L;
//		java.util.Optional<Ncs> result = ncsRepository.findById(ncsid);
//		Ncs ncs = result.orElseThrow();
//		log.info(ncs);
//	}
//}
