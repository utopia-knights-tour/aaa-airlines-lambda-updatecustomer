package service;

import java.sql.Connection;
import java.sql.SQLException;

import dao.CustomerDao;
import datasource.HikariCPDataSource;
import entity.Customer;

public class AgentService {

	public boolean updateCustomer(Customer customer) throws ClassNotFoundException, SQLException {
		Connection connection = null;
		try {
			connection = HikariCPDataSource.getConnection();
			CustomerDao customerDao = new CustomerDao(connection);
			int rowsModified = customerDao.update(customer);
			if (rowsModified != 1) {
				connection.rollback();
				return false;
			}
			connection.commit();
			return true;
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			connection.close();
		}
	}
}
