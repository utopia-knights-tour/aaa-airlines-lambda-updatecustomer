package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import entity.Customer;

public class CustomerDao {

	private Connection connection;

	public CustomerDao(Connection connection) {
		this.connection = connection;
	}

	public int update(Customer customer) throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = connection.prepareStatement(
				"UPDATE Customer SET customerName = ?, customerAddress = ?, customerPhone = ? WHERE customerId = ?",
				Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, customer.getCustomerName());
		pstmt.setString(2, customer.getCustomerAddress());
		pstmt.setString(3, customer.getCustomerPhone());
		pstmt.setLong(4, customer.getCustomerId());
		return pstmt.executeUpdate();
	}
}
