# Swiss-re case-study
# Description
This project helps to analyze the below after reading the csv input file(Id,firstName,lastName,salary,managerId)
- which managers earn less than they should, and by how much
- which managers earn more than they should, and by how much
- which employees have a reporting line which is too long, and by how much
# Build Steps
    1)Clone the code  from
        git clone https://github.com/Archana-Dadwal/swiss-re.git
    2)Build the project in local using
            mvn clean install
    3) While Running from IDE add "Absolute Path of (resources/large_employee_data.csv) File or any other csv file" to program arguments.
    4) while running from cmd
           java -classpath "Absolute Path of Generated Jar" org.swiss.CompanyStructure "Absolute path of the Test csv File(resources/large_employee_data.csv)"
