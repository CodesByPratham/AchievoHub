# AchievoHub 🚀

AchievoHub is a **Java-based web application** that enables users to manage their achievements, view other users' profiles, and keep track of their progress. This platform allows users to showcase their accomplishments in a structured manner, fostering a sense of motivation and growth. By providing an intuitive and interactive interface, AchievoHub ensures a seamless user experience for managing personal and professional achievements.
<br/><br/>

# 🛠️ Tools & Technologies Used
- **Programming Language:** Java (JDK 11 or higher)
- **Web Technologies:** JSP, Servlets, AJAX, jQuery
- **Database:** Oracle Database (JDBC for connectivity)
- **IDE:** NetBeans
- **Server:** Tomcat 10 or higher
- **Security:** jBCrypt for password hashing
- **Dependency Management:** Gson for JSON parsing
<br/><br/>

# 📽️ Page-wise Functionality

## **1️⃣ Registration Page**
- New users can sign up by providing required details.
- Real-time username & email availability check using AJAX.
<img src="https://github.com/CodesByPratham/AchievoHub/blob/main/resources/register.gif" height=400 width=800 alt="register" />
<br/>

## **2️⃣ Login Page**
- Allows users to enter credentials and log in securely.
- Provides error messages for incorrect credentials.
<img src="https://github.com/CodesByPratham/AchievoHub/blob/main/resources/login.gif" height=400 width=800 alt="login" />
<br/>

## **3️⃣ Profile Page**
- Displays user's information.
- Allows profile updates and picture uploads.
- Option to **delete profile** if needed.
<img src="https://github.com/CodesByPratham/AchievoHub/blob/main/resources/update%20profile.gif" height=400 width=800 alt="profile" />
<br/>

## **4️⃣ Add Achievements**
- Users can add new achievements with details.
- Supports **image uploads** for achievements.
<img src="https://github.com/CodesByPratham/AchievoHub/blob/main/resources/add%20achievement.gif" height=400 width=800 alt="add achievements" />
<br/>

## **5️⃣ View Achievements**
- Users can see all achievements added by them.
- Can also **view other users' achievements and profiles**.
<img src="https://github.com/CodesByPratham/AchievoHub/blob/main/resources/achievements.gif" height=400 width=800 alt="achievements" />
<br/>

## **7️⃣ Logout**
- Ends the user session and redirects to the login page.
<br/>

# 🛠️ Setup Instructions
## **1️⃣ Install Required Software**
- **NetBeans IDE** (or any Java IDE)
- **Tomcat 10 or higher**
- **Oracle Database**
- **JDK 11 or higher**
<br/>

## **2️⃣ Add Required JAR Files**
### 📥 **Download Dependencies:**
- **GSON**: [Download](https://repo1.maven.org/maven2/com/google/code/gson/gson/2.12.1/gson-2.12.1.jar)
- **jBCrypt**: [Download](https://repo1.maven.org/maven2/org/mindrot/jbcrypt/0.4/jbcrypt-0.4.jar)

### 📌 **Choose the Correct JDBC Driver for Your Database:**
| Database | JDK Version | JDBC Driver |
|----------|------------|-------------|
| **Oracle 9i** | Any | `classes12.jar` |
| **Oracle 10g (JDK 1.4)** | 1.4 | `ojdbc4.jar` |
| **Oracle 11g (JDK 1.5/1.6)** | 1.5/1.6 | `ojdbc5.jar` / `ojdbc6.jar` |
| **Oracle 12c/19c (JDK 1.7/1.8)** | 1.7/1.8 | `ojdbc7.jar` / `ojdbc8.jar` |
| **SQL Server (Various)** | 1.4 - 1.8 | `sqljdbc.jar` / `sqljdbc42.jar` |
| **IBM DB2** | Various | `db2jcc.jar`, `jt400.jar` |

🔗 Refer to official sources for JDBC downloads:
- [Oracle JDBC Drivers](https://www.oracle.com/database/technologies/appdev/jdbc-downloads.html)
- [JDBC Driver Selection Guide](https://docs.oracle.com/en/applications/jd-edwards/cross-product/9.2/eoism/obtain-jdbc-drivers.html)

### 📌 **Add JARs to NetBeans Project:**
1. Right-click **Libraries** in NetBeans.
2. Click **Add JAR/Folder**.
3. Select the downloaded JAR files and click **OK**.
<br/>

## **3️⃣ Configure `web.xml` (Database Credentials)**
📌 **Update `web.xml` with your database credentials:**
```xml
<context-param>
    <param-name>dbURL</param-name>
    <param-value>jdbc:oracle:thin:@//localhost:1521/FREEPDB1</param-value>
</context-param>
<context-param>
    <param-name>dbUser</param-name>
    <param-value>YOUR_DB_USERNAME</param-value>
</context-param>
<context-param>
    <param-name>dbPassword</param-name>
    <param-value>YOUR_DB_PASSWORD</param-value>
</context-param>
```
<br/>

## **4️⃣ Set Up & Run the Database**
📌 **Execute `DatabaseSetupServlet` to create tables & insert default data:**
1. **Start Tomcat Server** in NetBeans.
2. Open in browser:
   ```
   http://localhost:8080/AchievoHub/DatabaseSetupServlet
   ```
3. Wait for the success message: ✅ _"Database setup completed successfully!"_
<br/>

# 🔁 **Project Flow**
1️⃣ **Register** → Redirects to **Login**.  
2️⃣ **Login** → Redirects to **Profile Page** (User can update/delete profile).  
3️⃣ **Achievements Page** → View all achievements (own + others').  
4️⃣ **Add Achievements** → Users can add their achievements.  
5️⃣ **Update Achievements** → Modify previously added achievements.  
6️⃣ **Logout** → Ends session, redirects to login page.  
<br/>

# 🏆 **Final Thoughts**
AchievoHub is a **powerful and user-friendly achievement management system** that helps users **track their accomplishments and showcase their skills**. 🚀

Contributions, feedback, and feature requests are welcome! 😊

📌 **Future Enhancements (Planned)**

✅ Email verification using JavaMail API.  
✅ Password recovery feature.  
✅ REST API for external integrations.  
✅ Enhanced UI with React/Bootstrap.  

Happy Coding! 💻🎉
