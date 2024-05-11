/*
package com.fitapp.services.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import com.fitapp.services.models.CustomerRegistration;

@Repository
public class RegistrationDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void run(String... args) throws Exception {
		String sql = "INSERT INTO testapp (customername, customerid) VALUES ('Nam Ha Minh', '1')";

		int rows = jdbcTemplate.update(sql);
		if (rows > 0) {
			System.out.println("A new row has been inserted.");
		}
	}

	public Boolean saveEmployeeByPreparedStatement(CustomerRegistration e) {
		String query = "INSERT INTO public.mytestapp(\r\n"
				+ "customer_id, date_of_birth, gender, body_mass_index, fitness_goal, first_name, middle_name, last_name, height, height_units, weight, weight_units)\r\n"
				+ "	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

		return jdbcTemplate.execute(query, new PreparedStatementCallback<Boolean>() {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {

				ps.setInt(1, 2); 
				ps.setDate(2, e.getDateOfBirth());
				ps.setString(3, e.getGender());
				ps.setDouble(4, e.getBodyMassIndex());
				ps.setString(5, e.getFitnessGoal());
				ps.setString(6, e.customername.getFirstName());
				ps.setString(7, e.customername.getMiddleName());
				ps.setString(8, e.customername.getLastName());
				ps.setDouble(9, e.height.getHeight());
				ps.setString(10, e.height.getHeightUnits());
				ps.setDouble(11, e.weight.getWeight());
				ps.setString(12, e.weight.getWeightUnits());

				/*
				 long millis=System.currentTimeMillis();  
			     java.sql.Date date=new java.sql.Date(millis);
			        
			    ps.setInt(1, 1); 
				ps.setDate(2, date);
				ps.setString(3, "M");
				ps.setDouble(4, millis);
				ps.setString(5, "lean");
				ps.setString(6, "fname");
				ps.setString(7, "middlename");
				ps.setString(8, "getlastname");
				ps.setDouble(9, 6.0);
				ps.setString(10, "feet");
				ps.setDouble(11, 90.0);
				ps.setString(12, "kg");
               
				return ps.execute();

			}
		});
	}

}
*/