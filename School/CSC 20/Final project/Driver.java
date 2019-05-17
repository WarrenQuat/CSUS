/*Warren Quattrocchi
  Justin Roldan
  CSC 20
  Library main driver
*/

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.geometry.*;
import java.util.*;
import java.io.*;

public class Driver extends Application
{   
   //initial stage/scene
    Stage window;
    Scene populate, login, studentMenu, adminMenu;
    Student studentUser;
    Person adminUser;
    
    public static void main (String[] args)
    {
      launch(args);
    } 
        
    @Override
    public void start(Stage primaryStage) 
    {
      //initial list/object declaration-------------------------------------
      studentUser = new Student();
      adminUser = new Person();
      window = primaryStage;
      Administrator admins = new Administrator();
      Students students = new Students();
      Library libraries = new Library("Library 1");
      //--------------------------------------------------------------------
     
      //define populate scene-----------------------------------------------
      BorderPane root = new BorderPane();
      root.setStyle("-fx-background-color: #004E38");
      root.getChildren().clear();
      TextField studFile = new TextField();
      studFile.setPromptText("Please enter student file name");
      TextField adminFile = new TextField();
      adminFile.setPromptText("Please enter admin file name");
      TextField bookFile = new TextField();
      bookFile.setPromptText("Please enter book file name");
      Button studBtn = new Button("Submit");
      Button adminBtn = new Button("Submit");
      Button bookBtn = new Button("Submit");
      Button confirm = new Button("Continue");
      VBox prompts = new VBox();
      VBox buttons = new VBox();
      buttons.setPadding(new Insets(0,10,0,10));
      prompts.getChildren().addAll(studFile,adminFile,bookFile);
      buttons.getChildren().addAll(studBtn,adminBtn,bookBtn,confirm);
      root.setLeft(prompts);
      root.setCenter(buttons);
      populate = new Scene(root, 600,400);
      //------------------------------------------------------------------
      
      //Define student menu Scene---------------------------------
      BorderPane studentMenuPane = new BorderPane();
      Text userInfo = new Text(studentUser.toString());
      studentMenuPane.setStyle("-fx-background-color: #004E38");
      Button searchBtn = new Button("Search");
      Button checkedBtn = new Button("View your books");
      Button logOutBtn = new Button("Logout");
      userInfo.setStyle("-fx-fill: white");
      userInfo.setFont(new Font(15));
      HBox hbox = new HBox();
      HBox textH = new HBox();
      textH.setPadding(new Insets(100, 60 , 0 ,60));
      hbox.setPadding(new Insets(0,150,0,150));
      hbox.setSpacing(20);
      hbox.setStyle("-fx-background-color: #C6B880");
      textH.setStyle("-fx-border-color: #FFFFFF");
      hbox.getChildren().add(searchBtn);
      hbox.getChildren().add(checkedBtn);
      hbox.getChildren().add(logOutBtn);
      textH.getChildren().add(userInfo);
      studentMenuPane.setCenter(textH);
      studentMenuPane.setTop(hbox);
      studentMenu = new Scene(studentMenuPane, 600,400); 
     // window.setScene(studentMenu);         
      //---------------------------------------------------------
      
      //Define admin menu Scene----------------------------------
      BorderPane adminMenuPane = new BorderPane();
      Text adminInfo = new Text(adminUser.toString());
      adminMenuPane.setStyle("-fx-background-color: #004E38");
      Button addBookBtn = new Button("Add book");
      Button deleteBookBtn = new Button("Remove book"); 
      Button mngStudentBtn = new Button("Manage students");
      Button logOutBtn2 = new Button("Logout");    
      adminInfo.setStyle("-fx-fill: white");
      adminInfo.setFont(new Font(15));
      HBox adminHbox = new HBox();
      HBox adminText = new HBox();
      adminText.setPadding(new Insets(150, 60 , 0 ,60));
      adminHbox.setPadding(new Insets(0,130,0,130));
      adminHbox.setSpacing(5);
      adminHbox.setStyle("-fx-background-color: #C6B880");
      adminText.setStyle("-fx-border-color: #FFFFFF");
      adminHbox.getChildren().addAll(addBookBtn, deleteBookBtn, mngStudentBtn,logOutBtn2);
      adminText.getChildren().add(adminInfo);
      adminMenuPane.setCenter(adminText);
      adminMenuPane.setTop(adminHbox);
      adminMenu = new Scene(adminMenuPane, 600,400);   
      
      //admin menu event handlers---------------------------------------------------------------------------------
      logOutBtn2.setOnAction((ActionEvent e) ->
      {
         window.setScene(login);
         
      }); 
      
      //manage students button in admin menu
      /*
         initially will return a sorted array of students to main results pane
         if an id is selected it will send an arrayList of one student through 
         the same method used to display all students(displayMngStudents)
      */
      mngStudentBtn.setOnAction((ActionEvent e)->
      {
         
         BorderPane deletePane = new BorderPane();
         VBox backButton = new VBox();
         VBox results = new VBox();
         ScrollPane scroll = new ScrollPane(results);
         deletePane.setStyle("-fx-background-color: #004E38");
         Button back = new Button(" Back ");
         Button addStudent = new Button (" Add  ");
         backButton.getChildren().addAll(addStudent,back);
         deletePane.setLeft(backButton);  
         window.setScene(new Scene(deletePane, 600,400));
         results.setStyle("-fx-background-color: #C6B880");
         results.setStyle("-fx-fill:white");
         backButton.setStyle("-fx-border-color: #FFFFFF");
         Text returnPrompt = new Text("Click on a student to delete/set penalty");
         HBox returnPromptHBox = new HBox();
         TextField searchID = new TextField();
         Text filler = new Text("                           ");
         searchID.setPromptText("Search by ID");
         Button searchIDBtn = new Button("Search");
         Button clearBtn = new Button("Clear");
         deletePane.setBottom(returnPromptHBox);
         returnPromptHBox.setStyle("-fx-border-color: #FFFFFF");
         returnPrompt.setStyle("-fx-fill : white");
         returnPrompt.setFont(new Font(15)); 
         returnPromptHBox.getChildren().addAll(returnPrompt, filler,searchID,searchIDBtn,clearBtn); 
         students.sortStudents();
         ArrayList<Student> allStudents = students.returnStudents();           
         displayMngStudents(students, allStudents,results,deletePane);
         
         deletePane.setCenter(scroll);
         window.show();
         
         //return to previous screen  
         back.setOnAction((ActionEvent goBack)  ->{
           window.setScene(adminMenu);
         }); 
         
         //clear any searched id/return to initial student list
         clearBtn.setOnAction((ActionEvent clear) ->
         {
            students.sortStudents();  
            ArrayList<Student> allStudents2 = students.returnStudents(); 
            results.getChildren().clear();    
            displayMngStudents(students, allStudents,results,deletePane);
             
         });
         
         //search an id 
         searchIDBtn.setOnAction((ActionEvent ID) ->{
            //try to search for and catch an invalid ID and let user know it was not found
            try{
               students.sortStudents();
               Student s = students.search(searchID.getText());
               ArrayList<Student> searchedID = new ArrayList<Student>();
               searchedID.add(s);
               results.getChildren().clear();
               displayMngStudents(students,searchedID, results,deletePane);
            }catch (NotFoundException d)
            {
               searchID.clear();
               searchID.setPromptText("ID not found");
            }
         
         });
         
      addStudent.setOnAction((ActionEvent add ) ->{
        
        String emailPattern = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Button back2 = new Button("Back");
        BorderPane bookAddPane = new BorderPane();
        bookAddPane.setStyle("-fx-background-color: #004E38");
        TextField bookName = new TextField();
        bookName.setPromptText("Name");
        TextField bookAuthor = new TextField();
        bookAuthor.setPromptText("ID");
        TextField bookSubject = new TextField();
        bookSubject.setPromptText("Email");
        TextField bookDate = new TextField();
        bookDate.setPromptText("Phone");
        Text error = new Text();
        error.setFont(new Font(15));
        Button confirmBook = new Button("Continue");
        VBox bookPrompts = new VBox();
        VBox bookButtons = new VBox();
        bookButtons.setPadding(new Insets(0,10,0,10));
        bookPrompts.getChildren().addAll(bookName,bookAuthor,bookSubject, bookDate,error);
        bookButtons.getChildren().addAll(confirmBook,back);
        bookAddPane.setLeft(bookPrompts);
        bookAddPane.setCenter(bookButtons);
        window.setScene(new Scene(bookAddPane, 600,400));
        
        //all fields must not be null, all text fields will be sent to the addBook method
        confirmBook.setOnAction((ActionEvent addStud) ->{
         if(!bookName.getText().equals("") &&!bookDate.getText().equals("") && !bookAuthor.getText().equals("") && !bookSubject.getText().equals(""))
         {
            if(isValidID(bookAuthor.getText()) && isValidEmailAddress(bookSubject.getText()) && isValidPhone(bookDate.getText()))
            {
               addStudent(bookName.getText(), bookAuthor.getText(), bookSubject.getText(), bookDate.getText(), students);
               error.setText("Student succesfully added");
            }else{
               error.setText("One of the fields is invalid");
            }
         }else{
            error.setText("Please fill out all fields");
         } 
        }); 
        
         back2.setOnAction((ActionEvent goBack)  ->{
           window.setScene(adminMenu);
         });  
        });
                 
      });
      
      //delete a book button, displays a screen with every book
      deleteBookBtn.setOnAction((ActionEvent e) ->
      {
         BorderPane deletePane = new BorderPane();
         VBox backButton = new VBox();
         VBox results = new VBox();
         ScrollPane bookScroll = new ScrollPane(results);
         deletePane.setStyle("-fx-background-color: #004E38");
         Button back = new Button(" Back ");
         backButton.getChildren().add(back);
         deletePane.setLeft(backButton);  
         window.setScene(new Scene(deletePane, 600,400));
         results.setStyle("-fx-background-color: #C6B880");
         results.setStyle("-fx-fill:white");
         backButton.setStyle("-fx-border-color: #FFFFFF");
         Text returnPrompt = new Text("Click on a book to remove it");
         HBox returnPromptHBox = new HBox();
         deletePane.setBottom(returnPromptHBox);
         returnPromptHBox.setStyle("-fx-border-color: #FFFFFF");
         returnPrompt.setStyle("-fx-fill : white");
         returnPrompt.setFont(new Font(15)); 
         returnPromptHBox.getChildren().add(returnPrompt);             
         displayAllBooks(libraries,results);
         deletePane.setCenter(bookScroll);
         window.show();
            
         back.setOnAction((ActionEvent goBack)  ->{
           window.setScene(adminMenu);
         });  
           
      });
       
      //displays scene that prompts user for book details     
      addBookBtn.setOnAction((ActionEvent e ) ->{
        Button back = new Button("Back");
        BorderPane bookAddPane = new BorderPane();
        bookAddPane.setStyle("-fx-background-color: #004E38");
        TextField bookName = new TextField();
        bookName.setPromptText("Book name");
        TextField bookAuthor = new TextField();
        bookAuthor.setPromptText("Author");
        TextField bookSubject = new TextField();
        bookSubject.setPromptText("Subject");
        TextField bookDate = new TextField();
        bookDate.setPromptText("Date");
        Text error = new Text();
        error.setFont(new Font(15));
        Button confirmBook = new Button("Continue");
        VBox bookPrompts = new VBox();
        VBox bookButtons = new VBox();
        bookButtons.setPadding(new Insets(0,10,0,10));
        bookPrompts.getChildren().addAll(bookName,bookAuthor,bookSubject, bookDate,error);
        bookButtons.getChildren().addAll(confirmBook,back);
        bookAddPane.setLeft(bookPrompts);
        bookAddPane.setCenter(bookButtons);
        window.setScene(new Scene(bookAddPane, 600,400));
        
        
        //all fields must not be empty
        confirmBook.setOnAction((ActionEvent c) ->{
        if(!bookName.getText().equals("") &&!bookDate.getText().equals("") && !bookAuthor.getText().equals("") && !bookSubject.getText().equals(""))
         {
            addBookToLibrary(libraries,bookName,bookAuthor,bookSubject, bookDate);
            error.setText("Book succesfully added");
         }else{
            error.setText("Please fill out all fields");
         }
        });
        
        back.setOnAction((ActionEvent goBack)  ->{
          window.setScene(adminMenu);
        });
                     
      });
      //---------------------------------------------------------------------------------------------------------
      //---------------------------------------------------------
      
      //Button event handlers for populate scene-----------------------------------------------------------------------------------------------------------------------------------
      //if all files are entered, return to main
      confirm.setOnAction(new EventHandler<ActionEvent>()
      {
         @Override
         public void handle(ActionEvent event)
         {
              //display login scene if all files have been entered
              if(adminFile.getText().equals("File succesfully read") && studFile.getText().equals("File succesfully read")&& bookFile.getText().equals("File succesfully read") )
                  window.setScene(login); 
         }
         });
               
      //populate admin list
      adminBtn.setOnAction(new EventHandler<ActionEvent>()
      {         
            @Override
            public void handle(ActionEvent event) {
             try{
               File admin = new File(adminFile.getText());
               Scanner input = new Scanner (admin);
               adminFile.setText("File succesfully read");
               adminFile.setDisable(true);
               adminBtn.setDisable(true);
               populateAdmins(input, admins);              
            }catch (FileNotFoundException e){
               adminFile.clear();
               adminFile.setPromptText("File not found");
            }  
            }
        });  
        
       //populate student list
       studBtn.setOnAction(new EventHandler<ActionEvent>()
       {   
            @Override
            public void handle(ActionEvent event) {
             try{
               File student = new File(studFile.getText());
               Scanner input = new Scanner (student);
               studFile.setText("File succesfully read");
               studFile.setDisable(true);
               studBtn.setDisable(true);
               populateStudents(input,students);
             }catch (FileNotFoundException e){
               studFile.clear();
               studFile.setPromptText("File not found");
             }                
            }
        });   
      
       //populater book list within the library
       bookBtn.setOnAction(new EventHandler<ActionEvent>()
       {          
            @Override
            public void handle(ActionEvent event) {
             try{
               File book = new File(bookFile.getText());
               Scanner input = new Scanner (book);
               bookFile.setText("File succesfully read");
               bookFile.setDisable(true);
               bookBtn.setDisable(true);
               populateLibrary(input,libraries);

            }catch (FileNotFoundException e){
               bookFile.clear();
               bookFile.setPromptText("File not found");
            }                 
            }
        });              
        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------
        
       //define student menu button event handlers---------------------------
       logOutBtn.setOnAction((ActionEvent e) ->
       {
         window.setScene(login);
         
       });
       
       //shows user a list of every book they currently have checked out
       checkedBtn.setOnAction((ActionEvent getBooks) ->{
            studentUser.sortBooks();
            ArrayList<Book> booksCheckedOut = studentUser.getBooksCheckedOut();
            BorderPane returnPane = new BorderPane();
            VBox backButton = new VBox();
            VBox results = new VBox();
            returnPane.setStyle("-fx-background-color: #004E38");
            Button back = new Button(" Back ");
            backButton.getChildren().add(back);
            returnPane.setLeft(backButton);  
            window.setScene(new Scene(returnPane, 600,400));
            results.setStyle("-fx-background-color: #C6B880");
            results.setStyle("-fx-fill:white");
            backButton.setStyle("-fx-border-color: #FFFFFF");
            Text returnPrompt = new Text("Checked out books (" + studentUser.getPerson().getID() + ")");
            HBox returnPromptHBox = new HBox();
            returnPane.setBottom(returnPromptHBox);
            returnPromptHBox.setStyle("-fx-border-color: #FFFFFF");
            returnPrompt.setStyle("-fx-fill : white");
            returnPrompt.setFont(new Font(15)); 
            returnPromptHBox.getChildren().add(returnPrompt);           
            ScrollPane bookScroll = new ScrollPane(results);
            
            back.setOnAction((ActionEvent goBack)  ->{
                  window.setScene(studentMenu);
            });            
            
            //generates a button and temporary book for each item in the arrayList
            for(int i = 0; i < booksCheckedOut.size(); i++)
            {
               Book book = booksCheckedOut.get(i);
               Button b = new Button(booksCheckedOut.get(i).toString());
               results.getChildren().add(b);
               b.setOnAction((ActionEvent returnBook ) ->
               {
                  //return the chosen book
                  returnBook(studentUser,book.getName(), booksCheckedOut); 
                  b.setDisable(true);
               });
            }
           returnPane.setCenter(bookScroll);
           window.show();
           
           
        }); 
        
        //Search button action event------------------------------------------------------------           
        searchBtn.setOnAction((ActionEvent event) -> {
            BorderPane  searchPane = new BorderPane();
            searchPane.setStyle("-fx-background-color: #004E38");
            root.getChildren().clear();
            Button subGenre = new Button("Search");
            Button subAuthor = new Button("Search");
            Button subKeyword = new Button("Search");
            Button back = new Button(" Back ");
            TextField genre = new TextField();
            TextField author = new TextField();
            TextField keyWord = new TextField();
            genre.setPromptText("Search by genre");
            author.setPromptText("Search by author");
            keyWord.setPromptText("Search by key word");
            Text t = new Text("Search for a book to checkout (" + studentUser.getPerson().getID() + ")");
            VBox v1 = new VBox();
            VBox v2 = new VBox();
            HBox text = new HBox();
            VBox results = new VBox();
            v2.setStyle("-fx-background-color: #C6B880");
            v1.setStyle("-fx-border-color: #FFFFFF");
            t.setStyle("-fx-fill: white");
            t.setFont(new Font(15));
            text.setPadding(new Insets(100, 60 , 0 ,60));
            v2.getChildren().setAll(genre,author,keyWord);
            v1.getChildren().setAll(subGenre,subAuthor,subKeyword,back);
            text.getChildren().add(t);
            searchPane.setLeft(v1);
            searchPane.setCenter(v2);
            searchPane.setBottom(t);
            window.setScene(new Scene(searchPane, 600,400));      
            
                           
            back.setOnAction((ActionEvent goBack)  ->{
               window.setScene(studentMenu);
            });
            
            //search by Genre, calls searchbyGenre method      
            subGenre.setOnAction((ActionEvent genreSearch) ->{
               ScrollPane scroll = new ScrollPane(results);
               results.getChildren().clear();
               ArrayList<Book> searchedBooks = libraries.searchGenre(genre.getText());
               searchGenre(searchedBooks,results,studentUser); 
               searchPane.setRight(scroll);
               window.show();
                  
            }); 

            subAuthor.setOnAction((ActionEvent authorSearch) ->{
               ScrollPane scroll = new ScrollPane(results);
               results.getChildren().clear();
               ArrayList<Book> searchedBooks = libraries.searchAuthor(author.getText());
               searchAuthor(searchedBooks,results,studentUser); 
               searchPane.setRight(scroll);
               window.show();
                  
            }); 
            
            subKeyword.setOnAction((ActionEvent wordSearch) ->{
               ScrollPane scroll = new ScrollPane(results);
               results.getChildren().clear();
               ArrayList<Book> searchedBooks = libraries.searchKeyword(keyWord.getText());
               searchAuthor(searchedBooks,results,studentUser); 
               searchPane.setRight(scroll);
               window.show();
                  
            });             
            //------------------------------------------------------------------------------------- 
        });   
        //---------------------------------------------------------------------
        
        //define login/ define login scene-----------------------
        BorderPane loginPane = new BorderPane();
        loginPane.setStyle("-fx-background-color: #004E38");
        Button adminLogin = new Button("Administrator");
        Button studentLogin = new Button("Student");
        Button save = new Button("Save");
        TextField enterID = new TextField();
        enterID.setPromptText("Enter ID");
        HBox selection = new HBox();
        HBox saveBox = new HBox();
        saveBox.setPadding(new Insets(0,260,0,260));
        saveBox.getChildren().add(save);
        selection.setStyle("-fx-border-color: #FFFFFF");
        VBox log = new VBox();
        loginPane.getChildren().clear();
        log.setPadding(new Insets(100,15,100,15));
        selection.setPadding(new Insets(0,200,0,200));
        selection.setSpacing(40);
        selection.getChildren().add(adminLogin);
        selection.getChildren().add(studentLogin);
        log.getChildren().add(enterID);
        selection.setStyle("-fx-background-color: #C6B880"); 
        loginPane.setTop(selection);
        loginPane.setCenter(log);
        loginPane.setBottom(saveBox);
        login = new Scene(loginPane, 600,400);
        //------------------------------------------------------
        
        //action events for login scene-------------------------
        
        //Save all objects to respective DAT files
        save.setOnAction((ActionEvent e ) ->
        {
         try{
          save(students, libraries, admins);
          save.setText("Saved");
          save.setDisable(true);
         }catch (IOException io)
         {
            System.err.println(io.getMessage());
         }
        });
        
        adminLogin.setOnAction((ActionEvent event) -> {
            //display admin menu on Admin login
               try{
                  adminUser = admins.search(enterID.getText()); 
                  adminInfo.setText(adminUser.toString());
                  window.setScene(adminMenu);
                  enterID.clear();
               }catch (NotFoundException d){
                  enterID.clear();
                  enterID.setPromptText(d.getMessage()); 
             }
         });           
        
        studentLogin.setOnAction((ActionEvent event) -> {
            //display student menu on student login
              try{
                 studentUser = students.search(enterID.getText());
                 userInfo.setText(studentUser.getPerson().toString());
                 window.setScene(studentMenu);
                 enterID.clear();
              }catch (NotFoundException d){
                  enterID.clear();
                  enterID.setPromptText(d.getMessage());       
              }        
        });        
        //-------------------------------------------------------
        
        //initialize window with populate scene unless dat files are detected 
        if(!new File("students.dat").isFile() && !new File("admins.dat").isFile() && !new File("books.dat").isFile())
        {   
          window.setScene(populate);
        }else{     
          try{
          readFile(admins,students,libraries);
          window.setScene(login);
          }catch (IOException i)
          {
            System.err.println("no");
          }
        }
        window.show();
        window.setTitle("Library");
   } 
   
   
   
//Methods for searching/adding---------------------------------------------------------------------------------
   //Add a book to the current users cart
   public static void addBookToCart(Student studentUser, String bookDetails, ArrayList<Book> searchedBooks, Button b, int i)
   {
      ArrayList<Book> booksCheckedOut = studentUser.getBooksCheckedOut();
            //search through list a second time to make sure student doesn't already
            //have that book checked out
            for(int j = 0; j < booksCheckedOut.size(); j++)
             {
               if(booksCheckedOut.get(j).equals(searchedBooks.get(i)))
               {
                  b.setText("You already have this item checked out");
                  return;
               }
             }  
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Added");
            alert.setHeaderText("Book Succesfully checked out"); 
            alert.showAndWait();       
            studentUser.addBook(searchedBooks.get(i));
            b.setDisable(true);
            return;
   }  
   
   //Search a genre
   public static void searchGenre(ArrayList<Book> searchedBooks, VBox results, Student studentUser)
   {
       for(int i =0; i< searchedBooks.size(); i++)
       {
            int current = i;
            Button b = new Button(searchedBooks.get(i).toString());
            results.getChildren().add(b);
            b.setOnAction((ActionEvent book) ->{
               if(!studentUser.hasPenalty())
                  addBookToCart(studentUser, b.getText(), searchedBooks, b,current);
               else
                  b.setText("You cannot check out a book with a penalty");
            });   
       }
   }  
   
   //search for an author
   public static void searchAuthor(ArrayList<Book> searchedBooks, VBox results, Student studentUser)
   {
       for(int i =0; i< searchedBooks.size(); i++)
       {
            int current = i;
            Button b = new Button(searchedBooks.get(i).toString());
            results.getChildren().add(b);
            b.setOnAction((ActionEvent book) ->{
               if(!studentUser.hasPenalty())
                  addBookToCart(studentUser, b.getText(), searchedBooks, b,current);
               else
                  b.setText("You cannot check out a book with a penalty");
            });   
       }
   }  

   //search by a keyword
   public static void searchKeyword(ArrayList<Book> searchedBooks, VBox results, Student studentUser)
   {
       for(int i =0; i< searchedBooks.size(); i++)
       {
            int current = i;
            Button b = new Button(searchedBooks.get(i).toString());
            results.getChildren().add(b);
            b.setOnAction((ActionEvent book) ->{
               if(!studentUser.hasPenalty())
                  addBookToCart(studentUser, b.getText(), searchedBooks, b,current);
               else
                  b.setText("You cannot check out a book with a penalty");
            });   
       }
   }         
   //display all books in a library
   public static void displayAllBooks(Library libraries, VBox results)
   {
       libraries.sortBooks();
       LinkedList<Book> books = libraries.returnBooks();
       for(int i =0; i< books.size(); i++)
       {
            Button b = new Button(books.get(i).toString());
            results.getChildren().add(b);
            b.setOnAction((ActionEvent book) ->{
            libraries.removeBook(b.getText());
            b.setDisable(true);
            b.setText("Book Removed");
            });   
       }   
   }
   
   public static void displayMngStudents(Students students, ArrayList<Student> allStudents, VBox results,BorderPane pane)
   {
 
      for(int i = 0; i< allStudents.size(); i++)
      {
         Button b = new Button(allStudents.get(i).getPerson().toString());
         results.getChildren().add(b);
         Student s1 = allStudents.get(i);
         b.setOnAction((ActionEvent e) ->
         {
            try{
            Student s = students.search(s1.getPerson().getID()); 
            Button delete = new Button("Delete");
            Button setPenalty = new Button();
            if(s.hasPenalty())
            {
               setPenalty.setText("Remove penalty");
               setPenalty.setDisable(false);
            }
            else
            {
               setPenalty.setText("Set penalty"); 
               setPenalty.setDisable(false);
            }
            VBox buttons = new VBox();
            buttons.getChildren().addAll(delete,setPenalty);
            pane.setRight(buttons);
            buttons.setPadding(new Insets(0,40,0,40));         
            buttons.setStyle("-fx-background-color: #C6B880"); 
            
                       
            //delete selected student 
            delete.setOnAction((ActionEvent del) ->
            {
               students.remove(s);
               b.setDisable(true);
               setPenalty.setDisable(true);

            }); 
            //Set a penalty on student selected
            setPenalty.setOnAction((ActionEvent pen)->
            {
               s.setPenalty();
               setPenalty.setDisable(true);
            });
            }catch(NotFoundException ds)
            {
               System.out.println(ds.getMessage());
            }                
         });   
      }
   }
   
   //return a book   
   public static void returnBook(Student studentUser, String bookDetails,ArrayList<Book> booksCheckedOut)
   {
      //binary search through users currently checked out books
      int r = booksCheckedOut.size() - 1;
      int l = 0;
      while(l <= r)
      {
         int mid = (l + r)/2;
         if(booksCheckedOut.get(mid).getName().compareTo(bookDetails) == 0)
         {
            studentUser.removeBook(mid);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("returned");
            alert.setHeaderText("Book Succesfully returned"); 
            alert.showAndWait();
            return;
         }
         if(booksCheckedOut.get(mid).getName().compareTo(bookDetails) < 0)
            l = mid + 1;
         else{
            r = mid - 1; 
          }
       }
   }
   public static void addBookToLibrary(Library libraries, TextField bookName,TextField bookAuthor,TextField bookSubject,TextField  bookDate)
   {
      String name = bookName.getText();
      String author = bookAuthor.getText();
      String subject = bookSubject.getText();
      String date = bookDate.getText();
      Book b = new Book(name,author,subject,date);
      libraries.add(b); 
   }
   
   public static void addStudent(String name, String id, String email, String phone,Students students)
   {
      Student s = new Student(new Person(name, id,email,phone));
      students.add(s);
      
   }
   
   public static void populateAdmins(Scanner input, Administrator admins)
   {
     while (input.hasNextLine())
     {
      String name = input.nextLine();
      String id = input.nextLine();
      String email = input.nextLine();
      String phone = input.nextLine();
      Person p = new Person(name,id,email,phone);
      admins.add(p);
     }
   }  
   
   public static void populateStudents(Scanner input, Students students)
   {
     while (input.hasNextLine())
     {
      String name = input.nextLine();
      String id = input.nextLine();
      String email = input.nextLine();
      String phone = input.nextLine();
      Student s = new Student(new Person(name,id,email,phone));
      students.add(s);
      }
   }
   
   public static void populateLibrary(Scanner input, Library libraries)
   {
     while (input.hasNextLine())
     {
        String name = input.nextLine();
        String author = input.nextLine();
        String genre = input.nextLine();
        String date = input.nextLine();;
        Book b = new Book(name,author,genre,date);
        libraries.add(b);
      }            
   }   
   
   public static void save(Students students, Library libraries, Administrator admins)throws IOException
   {
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("students.dat"));
      out.writeObject(students.returnStudents());
      
      ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream("admins.dat"));
      out2.writeObject(admins.returnAdmins());
     
      ObjectOutputStream out3 = new ObjectOutputStream(new FileOutputStream("books.dat"));
      out3.writeObject(libraries.returnBooks());
   }
   
   public static void readFile(Administrator admins, Students students,Library libraries) throws IOException
   {
     try{
       //read students from dat file
       ObjectInputStream in = new ObjectInputStream(new FileInputStream("students.dat")); 
       ArrayList<Student> studTemp = (ArrayList<Student>)in.readObject();
       students.setStudents(studTemp);
             
       //read admins from dat file
       ObjectInputStream in2 = new ObjectInputStream(new FileInputStream("admins.dat"));
       ArrayList<Person> adTemp = (ArrayList<Person>) in2.readObject();
       admins.setAdmins(adTemp);
       //read books from dat file
       ObjectInputStream in3 = new ObjectInputStream(new FileInputStream("books.dat"));
       LinkedList<Book> libTemp = (LinkedList<Book>)in3.readObject(); 
       libraries.setBooks(libTemp);               
    }catch(ClassNotFoundException i)
    {
      System.err.println(i.getMessage());
    }
   }
   //validate entered email address 
   public boolean isValidEmailAddress(String email) {
           String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
           java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
           java.util.regex.Matcher m = p.matcher(email);
           return m.matches();
   }
   //validate entered ID 
   public boolean isValidID(String ID)
   {
      String idPattern = "\\d{5}";
      java.util.regex.Pattern p = java.util.regex.Pattern.compile(idPattern);
      java.util.regex.Matcher m = p.matcher(ID);
       return m.matches();
   }
   //validate entered phone number 
   public boolean isValidPhone(String phone)
   {
      String phonePattern = "\\d{3}-\\d{4}";
      java.util.regex.Pattern p = java.util.regex.Pattern.compile(phonePattern);
      java.util.regex.Matcher m = p.matcher(phone);
      return m.matches();    
   }
}
//-----------------------------------------------------------------------------------------------------------------