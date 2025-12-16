# AchievoHub 🚀

AchievoHub is a **Java-based web application** that enables users to manage their achievements, view other users' profiles, and keep track of their progress. This platform allows users to showcase their accomplishments in a structured manner, fostering a sense of motivation and growth. By providing an intuitive and interactive interface, AchievoHub ensures a seamless user experience for managing personal and professional achievements.

---

# 🛠️ Tools & Technologies Used

- **Programming Language:** Java (JDK 11 or higher)
- **Web Technologies:** JSP, Servlets, AJAX, jQuery
- **Database:** PostgreSQL Database (JDBC for connectivity)
- **IDE:** NetBeans
- **Server:** Tomcat 11 or higher
- **Security:** jBCrypt for password hashing
- **Dependency Management:** Gson for JSON parsing
- **Email Services:** Jakarta Mail (Now as Angus Mail) for **email verification, password change, and forgot password functionality**

---

# 📽️ Page-wise Functionality

## **1️⃣ Registration Page**

- New users can sign up by providing required details.
- Real-time username & email availability check using AJAX.
- Email verification using Angus Mail API before account activation.
<img src="https://github.com/CodesByPratham/AchievoHub/blob/main/resources/register.gif" height=400 width=800 alt="register" />

## **2️⃣ Login Page**

- Allows users to enter credentials and log in securely.
- Provides error messages for incorrect credentials.
<img src="https://github.com/CodesByPratham/AchievoHub/blob/main/resources/login.gif" height=400 width=800 alt="login" />

## **3️⃣ Profile Page**

- Displays user's information.
- Allows profile updates and picture uploads.
- Option to **delete profile** if needed.
- Functionality to change the password directly from the profile page.
<img src="https://github.com/CodesByPratham/AchievoHub/blob/main/resources/update%20profile.gif" height=400 width=800 alt="profile" />

## **4️⃣ Add Achievements**

- Users can add new achievements with details.
- Includes **image upload functionality with drag and drop**.
<img src="https://github.com/CodesByPratham/AchievoHub/blob/main/resources/add%20achievement.gif" height=400 width=800 alt="add achievements" />

## **5️⃣ View Achievements**

- Users can see all achievements they have added.
- Can also **view other users' achievements and profiles**.
<img src="https://github.com/CodesByPratham/AchievoHub/blob/main/resources/achievements.gif" height=400 width=800 alt="achievements" />

## **6️⃣ Logout**

- Ends the user session and redirects to the login page.

---

# 🛠️ Setup Instructions

## **1️⃣ Install Required Software**

- **NetBeans IDE** (or any Java IDE)
- **Tomcat 11 or higher**
- **PostgreSQL Database**
- **JDK 11 or higher**

## **2️⃣ Add Required JAR Files**

### 📥 **Download Dependencies:**

- **GSON**: [Download](https://repo1.maven.org/maven2/com/google/code/gson/gson/2.12.1/gson-2.12.1.jar)
- **jBCrypt**: [Download](https://repo1.maven.org/maven2/org/mindrot/jbcrypt/0.4/jbcrypt-0.4.jar)
- **Jakarta Mail (Angus Mail Provider)**: [Download](https://repo1.maven.org/maven2/org/eclipse/angus/jakarta.mail/2.0.3/jakarta.mail-2.0.3.jar)
- **Jakarta Activation API 2.1 Specification**: [Download](https://repo1.maven.org/maven2/jakarta/activation/jakarta.activation-api/2.1.3/jakarta.activation-api-2.1.3.jar)

🔗 Refer to official sources for JDBC driver downloads:

- [PostgreSQL JDBC Drivers](https://mvnrepository.com/artifact/org.postgresql/postgresql)
- [PostgreSQL](https://jdbc.postgresql.org/)

### 📌 **Add JARs to NetBeans Project:**

1. Right-click **Libraries** in NetBeans.
2. Click **Add JAR/Folder**.
3. Select the downloaded JAR files and click **OK**.

---

## **3️⃣ Configure `web.xml` (Database & SMTP Credentials)**

📌 **Update `web.xml` with your database credentials:**

```xml
<!-- Start: Database Credentials -->
    <context-param>
        <param-name>dbDriver</param-name>
        <param-value><!-- Add your database driver like "org.postgresql.Driver" --></param-value>
    </context-param>
    <context-param>
        <param-name>dbURL</param-name>
        <param-value><!-- Add your database url like "jdbc:postgresql://localhost:5432/achievohub" --></param-value>
    </context-param>
    <context-param>
        <param-name>dbUser</param-name>
        <param-value><!-- Add your database username like "SYS" --></param-value>
    </context-param>
    <context-param>
        <param-name>dbPassword</param-name>
        <param-value><!-- Add your database password like "ADMIN123" --></param-value>
    </context-param>
    <!-- End: Database Credentials -->
    <!-- Start: SMTP Credentials -->
    <context-param>
        <param-name>mailHost</param-name>
        <param-value><!-- Add your SMTP mail host like "smtp.gmail.com" --></param-value>
    </context-param>
    <context-param>
        <param-name>mailPort</param-name>
        <param-value><!-- Add your SMTP mail port like "587" --></param-value>
    </context-param>
    <context-param>
        <param-name>mailUser</param-name>
        <param-value><!-- Add your SMTP mail user like "example123@gmail.com" --></param-value>
    </context-param>
    <context-param>
        <param-name>mailPassword</param-name>
        <param-value><!-- Add your SMTP mail password like "abcd efgh ijkl mnop" --></param-value>
    </context-param>
<!-- Start: SMTP Credentials -->
```

---

## **4️⃣ Set Up & Run the Database**

📌 **Execute `DatabaseSetupServlet` to create tables & insert default data:**

1. **Start Tomcat Server** in NetBeans.
2. Open in browser:
   ```
   http://localhost:8080/AchievoHub/DatabaseSetupServlet
   ```
3. Wait for the success message: ✅ _"Database setup completed successfully!"_

---

# 🏆 **Final Thoughts**

AchievoHub is a **powerful and user-friendly achievement management system** that helps users **track their accomplishments and showcase their skills**. 🚀

Contributions, feedback, and feature requests are welcome! 😊

📌 **Future Enhancements (Planned)**
✅ REST API for external integrations.  
✅ Enhanced UI with React/Bootstrap.  

Happy Coding! 💻🎉