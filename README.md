# UniApp (Java Application) (H1)
UniApp is a Java application developed as part of a project for the Hellenic Open University (HOU). It processes university data from around the world using the University Domains and Names Data List API. The application provides a graphical user interface (GUI) with various features for searching and managing universities.

## Features 
- University search by name
  
  ![search_by_name-ezgif com-crop (4)](https://github.com/user-attachments/assets/efe3f502-8482-42ef-8e86-7e1e91fe04d6)

- Search for universities by country and apply filters

  ![bycountry-ezgif com-crop](https://github.com/user-attachments/assets/e97d03c8-1c0a-4e03-a2f0-0a51b0a3296b)

- Update and include additional details for a university

  ![2025-03-0901-43-38-ezgif com-crop](https://github.com/user-attachments/assets/5daaf09e-34c6-436f-acc0-78d4a0006441)

- View and export the statistics of your search to PDF

  ![2025-03-0901-48-27-ezgif com-crop](https://github.com/user-attachments/assets/d7a0fb60-14cd-4eae-b70f-3994112951e2)

## Technologies Applied

- Java for the implementation of the application
- Swing for the GUI
- JPA (EclipseLink) for data storage
- Apache Derby as the database
- Retrofit for executing HTTP requests
- iText for exporting statistics to PDF

## Api

The application uses the [University Domains and Names Data List API](https://github.com/Hipo/university-domains-list-api) to retrieve university data. An example of an API call to search for universities in Greece is as follows:

http://universities.hipolabs.com/search?country=greece

The returned data is in JSON format

## Project Structure

The project follows the MVVM (Model-View-ViewModel) architecture.

## Execution

1. Clone the repository:
   
```bash
git clone https://github.com/Kyriakopoulos-Andreas/Uni_App.git
```
2. Open the project in NetBeans
   
3. Run the file UniApp.java

## Note

The required libraries are included locally in the project, and no additional installation is needed.

