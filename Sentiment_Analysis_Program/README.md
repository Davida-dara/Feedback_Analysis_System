# WHITE PANDA -Sentiment Analysis Software

WHITE PANDA Sentiment analysis tool is a software with the main purpose of analysing the sentiment behind customers feedbacks.
However this software is not just restricted to analysing sentiment, having
many other features which are unlocked depending on what type of user you are logged in as.
With two possible users, The admin, being able to control access and login details into the system, and the Team Member.
An employee of the white panda company tasked with analysine the sentiment within customer's feedback.
## Installation
Download the Zipped folder containing all the project files. Extract and import into your preferred IDE.
This project was created Using the Intellij IDE on a Windows 11 Operating System.
The project is run through the main.java class

## Usage
````Java
import Sentiment_Analysis_Program
        
# starts the program 
Session.runSession();

# creates a user object having a name and an emailaddress
User newUser = new User(emailAddress, "temp name");

# runs the an admin.s session of the program
HRSession.runSession();

# runs a Team Members Version of the program
TMSession.runSession();

# creates a report analysis object to analyse given text
AnalysisReport report = new AnalysisReport(data);
````

## Key Usage Instructions
#### Logging in to the Admin Account:
To log in to the Admin account enter Username: admin@gmail.com and Password: 123456
As an admin a user is able to add and remove a new Team Member login details
#### Logging in to the Team member Account:
Use the following details to log in as a team member:
Username: Testing@gmail.com; Password : Tt024;
A team member is able to analyse customer feedback, generate analysis reports,
view and save analysis reports. They can also view previously analysed reports.

#### View Profile:
Both the team member and the admin sessions allow the user to veiw their profile.
by selecting the third option in the menu the user can see their personal info
such as their name and email address.

Signing in with a different account and terminating the program are also options that are available in
both the admin and team member sessions.

### Using the Team Member account
Operating the team member account can be quite tricky, to simplify things and help you
understand how the program is run, here are a few guiding instructions:
Once are successfully logged in as a user,
The menu option would be displayed.

#### Option 1: Analyse Feedback
You can chose enter the data you would like to analyse either bu entering it directly into the console
or entering the name of the file which the data is stored. An example of such file would be test.txt, which contains some data, yet to be entered.
by entering test.txt when prompted for a file name, the system will analyze the contents of the file.
after which the user will be prompted on weather or not they would like to save generated report. Considdet saving the generated report
by following the on screen prompts and eventually viewing the saved report when selecting menu option 2.

#### Option 2: View Analysed Reports
Previously generated analysis report are retrievable. the list
of recent reports generated would be displayed and the user is then
prompted to make a selection of the files displayed. The selected 
file is displayed and available for member viewing. feedback1.txt and feedback2.tx
are two generated analysis reports  that are also available for viewing. 
User is suggested to try opening up this previously generated report.
            

## Acknowledgements
This application makes use of the Stanford NLP Library. 
