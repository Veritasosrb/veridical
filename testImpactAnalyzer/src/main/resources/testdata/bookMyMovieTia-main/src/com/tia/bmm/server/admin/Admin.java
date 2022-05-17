/**
 * 
 */
package com.tia.bmm.server.admin;

import java.util.Scanner;


/**
 * @author aditi
 *
 */
public class Admin {
	final String adminEmailId="aditimantri04@gmail.com";
    final String password="Aditi123@";

    public boolean loginAsAdmin() {
        Scanner sc = new Scanner(System.in);
        try{
            String eid,pw;
            System.out.print("\nEnter Admin emailid: ");
            eid = sc.nextLine();
            if(eid.equals(adminEmailId)) {
                try {
                    System.out.print("Enter Password: ");
                    pw = sc.nextLine();
                    if(pw.equals(password)) return true;
                    else {
                        throw new InvalidAdminDetails("Invalid Password!");
                    }
                }
                catch(InvalidAdminDetails exc) {
                    System.out.println(exc);
                    return false;
                }
            }
            else {
                throw new InvalidAdminDetails("Invalid Admin emailid!");
            }
        }
        catch(InvalidAdminDetails exc) {
            System.out.println(exc);
            return false;
        }
    }
}
