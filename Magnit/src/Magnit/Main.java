package Magnit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Provider;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	public static void main(String[] args) throws IOException {
		System.out.println("Введите число:");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int a = Integer.parseInt(reader.readLine());
		long start = System.currentTimeMillis();
		DBConnect dbConnect = new DBConnect();
		dbConnect.setN(a);
		dbConnect.setConnection();
		dbConnect.fillInTable();
		XMLService.createXML(dbConnect);
		dbConnect.closeConnection();
		XMLService.transformXML();
		System.out.println("Сумма = " + XMLService.parseXML());
		long finish = System.currentTimeMillis();
		long time = finish - start;
		System.out.println("Время выполнения " + time + " ms");
	}
}

