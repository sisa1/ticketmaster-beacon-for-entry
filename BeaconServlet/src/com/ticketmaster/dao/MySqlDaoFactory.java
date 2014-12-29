/*
 * @AUTHOR: Matthew de la Rosa
 * @DATE: Aug 2014
 * 
 * @DESCRIPTION:
 * 		DaoFactory produces My SQL implementation of DAOs
 * 		Return types are generic DAO
 */

package com.ticketmaster.dao;

import com.ticketmaster.dao.myql.UserDaoImpl;

public class MySqlDaoFactory {

	public static UserDao getUserDAO() {
		return new UserDaoImpl();
	}
}