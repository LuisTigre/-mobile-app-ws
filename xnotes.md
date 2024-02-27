# Instructions of how to switch from jar to war package
1 - Make changes the main method by extending it as it follows:

```java
@SpringBootApplication
public class MobileAppWsApplication extends SpringBootServletInitializer {}
```
2 - Override the following method and put it at the top  as it follows:

```java
@SpringBootApplication
public class MobileAppWsApplication extends SpringBootServletInitializer {
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
        return	application.sources(MobileAppWsApplication.class);
    }
    
   //   rest of your codes can go her
}
```
3 - Add this to the pom.xml file 

```xml 
    <packaging>war</packaging>
```

- Place it between between version and name tags as displayed bellow

```xml 
    <version>0.0.1-SNAPSHOT</version>
    <packaging>war</packaging>
    <name>mobile-app-ws</name>
```

- Add this dependency at bottom in the dependencies sections

```xml 
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-tomcat</artifactId>
        <scope>provided</scope>
    </dependency>
```

4 - Run bellow command to delete the target file 

```bash
    mvn clean
```
5 - To build the progect and generate the target file again run this: 

```bash
    mvn install
```
# How To Install Maven with java 17 in Windows 10:
- Link: https://www.youtube.com/watch?v=T91EBAZjR18


- How to enable Apache Tomcat to accept war files more than pre default size of 50mb

1- Go to folder:
```shell
    C:\apache-tomcat-10\webapps\manager\WEB-INF
```
2- Edit the file web.xml:
 - This the default  size of 50mb it means the the server can only take war file with maximun size of 50mb
```xml
<multipart-config>
    <!-- 50MB max -->
    <max-file-size>92428800</max-file-size>
    <max-request-size>92428800</max-request-size>
    <file-size-threshold>0</file-size-threshold>
</multipart-config>

```
- To change it to 200mb of size for example replace it with:

```xml
<multipart-config>
    <!-- 200MB max -->
    <max-file-size>209715200</max-file-size>
    <max-request-size>209715200</max-request-size>
    <file-size-threshold>0</file-size-threshold>
</multipart-config>

```

# How to connect to AWS with SSH

1 - Run this command using gitbush terminal

- To enable Super User:
```shell
sudo su
```

```shell
ssh -i PhotoAppEC2Server.pem ec2-user@ec2-54-80-55-227.compute-1.amazonaws.com
```

2 - Run
- To check updates
```shell
sudo yum update
```
# Java instalation on AWS
- To check the java version installed in AWS EC2 
```shell
sudo java -version
```
- To list the available java versions available
```shell
sudo yum list java*
```
- To install the available java image
```shell
 sudo yum install java-17-amazon-corretto

```
- or
```shell
sudo yum install java-1.8.0

```
- To switch between java versions run cmd bellow 
```shell
sudo /usr/sbin/alternatives --config java

```
- To set the java version as default 
```shell
sudo /usr/sbin/alternatives --config javac
```
# Tomcat Download
- To check the current directory
```shell
 pwd #home/ec2-user (expected output) 
```

- To download apache 10 binary which is compatible with java 17 in `home/ec2-user`
```shell
 sudo wget https://dlcdn.apache.org/tomcat/tomcat-10/v10.1.19/bin/apache-tomcat-10.1.19.tar.gz 
```
# Tomcat 10 Installation
- To extract the tar file run
```shell
sudo tar -xvzf apache-tomcat-10.1.19.tar.gz
```

# Creating User in the OS System for Tomcat 

- To create new Group run
```shell
sudo groupadd --system tomcat
```

- To create new User run
```shell
sudo useradd -d /home/ec2-user/apache-tomcat-10.1.19 -r -s /bin/false -g tomcat tomcat
```
- To update tomcat permissions for the user
```shell
sudo chown -R tomcat:tomcat /usr/share/apache-tomcat-10.1.19
```
- To delete the user or group:
```shell
sudo userdel tomcat
sudo userdel -r tomcat # if user has home directory
sudo groupdel tomcat
```

- Create Tomcat service by creating Unit file
```shell
sudo vi /etc/systemd/system/tomcat.service
```
- Past the following content: To activate editor mode:`shift + i` To save:`shift + zz`

```text
[Unit]
Description=Apache Tomcat Web Application Container
After=network.target

[Service]
Type=forking

Environment=CATALINA_PID=/home/ec2-user/apache-tomcat-10.1.19/temp/tomcat.pid
Environment=CATALINA_HOME=/home/ec2-user/apache-tomcat-10.1.19
Environment=CATALINA_BASE=/home/ec2-user/apache-tomcat-10.1.19

ExecStart=/home/ec2-user/apache-tomcat-10.1.19/bin/startup.sh
ExecStop=/home/ec2-user/apache-tomcat-10.1.19/bin/shutdown.sh

User=tomcat
Group=tomcat

[Install]
WantedBy=multi-user.target
```

- Alternative: `sudo nano /etc/systemd/system/tomcat.service` To save the file and exit in Nono editor type:
```shell
ctr + x # and yes 
```
# Running the Tomcat server via startup.sh

- To enter the folder type:
```shell
cd apache-tomcat-10.1.19/bin
```

- To start the Tomcat server via sh
```shell
./startup.sh
```
- To stop the Tomcat server via sh
```shell
./shutdown.sh
```

- To update tomcat permissions for the user
```shell
sudo sudo chown -R tomcat:tomcat /home/ec2-user/apache-tomcat-10.1.19
```

# Adding service management tool (CTL)

- To enable a lots of Permission in catalina.out
```shell
cd /home/ec2-user/apache-tomcat-10.1.19
chown -R tomcat6 webapps temp logs work conf
chmod -R 777 webapps temp logs work conf
```

- Run The Following Command:

```shell
sudo systemctl daemon-reload
```
- To check the service status run:
```shell
sudo systemctl status tomcat
```

- To Enable the service run:
```shell
sudo systemctl enable tomcat
```
- To Start the service run:

```shell
sudo systemctl start tomcat
```
- To Stop the service run:

```shell
sudo systemctl stop
```

# Creating user in Tomcat Server

- Find context
```shell
find / -name context.xml
```
```shell
cd /home/ec2-user/apache-tomcat-10.1.19/webapps/manager/META-INF
```
```shell
cd /home/ec2-user/apache-tomcat-10.1.19/webapps/host-manager/META-INF
```
- For each file use bellow:
```shell
vi context.html
```
- Comment the section as follows
```xml
 <!--<Valve className="org.apache.catalina.valves.RemoteAddrValve"
  allow="127\.\d+\.\d+\.\d+|::1|0:0:0:0:0:0:0:1" />-->
```
```shell
cd /home/ec2-user/apache-tomcat-10.1.19/conf
```
- Create Tomcat users 
```shell
vi tomcat-users.xml
```
- Paste this:
```xml
<role rolename="admin-gui, manager-gui"/>
<user username="admin" password="admin" roles="admin-gui, manager-gui"/>
```

# Other options (This exemple is incomplete)

- To extract the tar file run
```shell
sudo tar xvf apache-tomcat-10.1.19.tar.gz -C /usr/share
```
- To see the extracted the tar file run
```shell
sudo ls -lrt /usr/share
```
- To rename it to a short name run:
```shell
sudo ln -s /usr/share/apache-tomcat-10.1.19 /usr/share/tomcat10
```


# Creating Tomcat User on AWS (Optional)

- To create new Group run 
```shell
sudo sudo groupadd --system tomcat
```
- To create new User run
```shell
sudo useradd -d /usr/share/tomcat10 -r -s /bin/false -g tomcat tomcat
```
- To update tomcat permissions for the user
```shell
sudo sudo chown -R tomcat:tomcat /usr/share/apache-tomcat-10.1.19
```

# Creating service file on Tomcat AWS (Optional)
link: https://www.udemy.com/course/restful-web-service-with-spring-boot-jpa-and-mysql/learn/lecture/10545670#overview
- This will allow service to start when the system boots
- run :
```shell
sudo vi /etc/systemd/system/tomcat10.service
```
- the content should be as follows:

```text
[Unit]
Description=Tomcat Server
After=syslog.target network.target

[Service]
Type=forking
User=tomcat
Group=tomcat

Environment=JAVA_HOME=/usr/lib/jvm/jre
Environment='JAVA_OPTS=-Djava.awt.headless=true'
Environment=CATALINA_HOME=/usr/share/tomcat10
Environment=CATALINA_BASE=/usr/share/tomcat10
Environment=CATALINA_PID=/usr/share/tomcat10/temp/tomcat.pid
Environment='CATALINA_OPTS=-Xms512M -Xmx1024M'
ExecStart=/usr/share/tomcat10/bin/catalina.sh start
ExecStop=/usr/share/tomcat10/bin/catalina.sh stop

[Install]
WantedBy=multi-user.target
```
***** To save and quit the edit mode write: *****
```shell
Vim:

In Vim, you can save a file by entering the command mode by pressing Esc, 
then typing :w and pressing Enter.
Alternatively, you can use :wq to save and quit or ZZ (shift + zz) to save and exit.
```
- Run The Following Command:

```shell
sudo systemctl daemon-reload
```


- To Enable the service run:

```shell
sudo systemctl enable tomcat10
```
- To Start the service run:

```shell
sudo systemctl start tomcat10
```
- To Stop the service run:

```shell
sudo systemctl stop
```
# Installing MariaDB Server on EC2 AMI 2023

- To download
```shell
sudo yum update -y
sudo yum install -y mariadb105-server
```
- To enable in System CTL to start in System boot
```shell
sudo systemctl enable mariadb
sudo systemctl start mariadb
sudo systemctl restart mariadb
sudo systemctl stop mariadb
sudo systemctl status mariadb
```
- To setup own mysql user password
```shell
sudo mysql_secure_installation
```
`NOTE: choose yes to all options`

- To login mysql type:
```shell
mysql -u root -p;
```
`mysql> exit # press exit to close mysql console`

- To create a new user for the remote project
```shell
create user 'lewis'@'localhost' identified by 'admin';
```
- Setup privileges
```shell
grant all privileges on photo_app.* to 'lewis'@'localhost';
```
- In order the privileges to take effect run:
```shell
flush privileges;
```
- To compile the war project
```shell
mvn install
```

# Debugging cmd
- To inspect Catalina log file
```shell
less /home/ec2-user/apache-tomcat-10.1.19/logs/catalina.out
```
- To clear log file
```shell
/home/ec2-user/apache-tomcat-10.1.19/logs/catalina.out
```

# Deploying WAR to AWS Elastic Beanstalk

# Database
- Search For RDS to create the database
- select /EC2/Security Groups/ name_of_security_gr/edit inbound rules
- Set Type=Mysql/Aurora, Source=Anywhere
- Connection to the RD database Url 
```shell
spring.datasource.url=jdbc:mysql://photoappusersapi.cz4w00gyohyf.us-east-1.rds.amazonaws.com:3306/photoappusersapi
```
# Application creation and deploy
- Search for Elastic Beantalk 
- Provide App name -> Platform=Tomcat -> upload code
- To delete Env via console:
```shell
aws elasticbeanstalk delete-environment --environment-name PhotoAppUsersApi-env --terminate-resources
```
- Updated video for Access configuration link: https://www.youtube.com/watch?v=zjy9Pv_bDds
- To create EC2 instance select: create and use new service role 
- To create new profile go to: services/security identity & compliance/( open in new tab)
- Select: roles/aws service/service or use case=EC2->Next
- Add bellow policies and save as for example: `elastic-beanstak-ec2-role `
```javascript
AWSElasticBeanstalkRoleRDS
AWSElasticBeanstalkRoleSNS
AWSElasticBeanstalkRoleWorkerTier
AWSElasticBeanstalkWebTier
AWSElasticBeanstalkWorkerTier
```
-Add the new EC2 instance profile: `elastic-beanstak-ec2-role` and next->skip->submit