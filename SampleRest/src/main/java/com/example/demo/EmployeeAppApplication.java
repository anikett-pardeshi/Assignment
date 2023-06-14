package com.example.demo;

import com.example.demo.helper.LoadExcelIntoDataBase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;

@SpringBootApplication
public class EmployeeAppApplication {

	public static void main(String[] args) {
		System.out.println("public static void main(String[] args)");
		SpringApplication.run(EmployeeAppApplication.class, args);
		try {
			LoadExcelIntoDataBase.LoadData();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

	}

}
