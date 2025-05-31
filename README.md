# 💰 ExpenseTracker Web Application

A complete expense tracking web application built with Spring Boot backend and vanilla JavaScript frontend.

## 🚀 Features

### 🔐 User Authentication
- User registration with name and email
- Simple login system
- User session management
- **Admin privilege system** with role-based access controls
- Admin user identification with visual badges

### 💰 Expense Management
- Add new expenses with amount, category, date, description, and location
- View all your expenses in a clean table format
- Delete expenses
- Real-time expense calculations

### 🏷️ Category Management
- Pre-loaded categories (Travel, Auto Loan, Student Loan)
- Add custom categories
- Dropdown selection when adding expenses
- **Admin-only category deletion** with permission controls
- Visual admin badges and privilege indicators

### 📊 Dashboard Features
- Total expense amount display
- Expense count
- Category-wise organization
- Responsive design

## 🛠️ Technologies Used

### Backend
- **Spring Boot 2.7.18** - Main framework
- **Spring Data JPA** - Database operations
- **H2 Database** - In-memory database
- **Lombok** - Boilerplate code reduction
- **Maven** - Dependency management

### Frontend
- **HTML5** - Structure
- **CSS3** - Styling with modern design
- **Vanilla JavaScript** - Functionality and API calls
- **Responsive Design** - Works on all devices

## 🏃‍♂️ How to Run

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Running the Application

1. **Clone/Navigate to the project directory:**
   ```bash
   cd /path/to/ExpenseTracker-React-Springboot
   ```

2. **Run the Spring Boot application:**
   ```bash
   mvn spring-boot:run
   ```

3. **Access the application:**
   - Web Interface: http://localhost:8080
   - H2 Database Console: http://localhost:8080/h2-console
   - API Base URL: http://localhost:8080/api

### Database Access
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** (leave blank)

## 🎯 How to Use the Web Application

### 1. Getting Started
1. Open http://localhost:8080 in your browser
2. You'll see the login/register interface

### 2. Register a New User
1. Click on the "Register" tab
2. Enter your full name and email address
3. Click "Register"
4. You'll be automatically logged in

### 3. Login (if you already have an account)
1. Stay on the "Login" tab
2. Enter your email and name
3. Click "Login"

### 4. Adding Expenses
1. After login, you'll see the main dashboard
2. Fill in the "Add New Expense" form:
   - **Description:** What the expense was for (e.g., "Coffee", "Gas")
   - **Amount:** How much you spent
   - **Date:** When the expense occurred
   - **Location:** Where you spent the money
   - **Category:** Select from dropdown (Travel, Auto Loan, Student Loan, Food, etc.)
3. Click "Add Expense"
4. The expense will appear in your expenses table

### 5. Managing Expenses
- **View Expenses:** All your expenses are displayed in a table
- **Delete Expenses:** Click the "Delete" button next to any expense
- **Track Totals:** See your total expenses and count at the top

### 6. Adding Categories
1. In the "Add New Category" section
2. Enter a category name (e.g., "Food", "Entertainment", "Bills")
3. Click "Add Category"
4. The new category will be available in the dropdown when adding expenses

### 7. Managing Categories (Admin Only)
1. **Admin Login Required:** Login with `admin@auth.com` / `Admin`
2. **Admin Badge:** Look for green "ADMIN" badge next to username
3. **Category List:** View all categories in the "Manage Categories" section
4. **Delete Categories:** 
   - Click red "Delete" button next to any category
   - Confirm deletion in the popup dialog
   - Category is permanently removed from the system
5. **Permission Control:** Regular users see "Admin only" text instead of delete buttons

### 8. User Roles and Permissions
- **Regular Users:** Can add expenses, create categories, but cannot delete categories
- **Admin Users:** Full access including category deletion privileges
- **Visual Indicators:** Admin users are clearly identified with badges and enhanced UI elements

## 📡 API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user

### Users
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `POST /api/users` - Create user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### Expenses
- `GET /api/expenses` - Get all expenses
- `GET /api/expenses/{id}` - Get expense by ID
- `GET /api/expenses/user/{userId}` - Get expenses by user
- `POST /api/expenses` - Create expense
- `POST /api/expenses/create` - Create expense with parameters
- `PUT /api/expenses/{id}` - Update expense
- `DELETE /api/expenses/{id}` - Delete expense

### Categories
- `GET /api/categories` - Get all categories
- `GET /api/categories/{id}` - Get category by ID
- `POST /api/categories` - Create category
- `PUT /api/categories/{id}` - Update category
- `DELETE /api/categories/{id}?userEmail={email}` - **Delete category (Admin only)**
- `GET /api/categories/exists/name/{name}` - Check if category name exists

## 🗂️ Sample Data

The application comes with pre-loaded sample data:

### Users
- **Siamak** (Codeengine11@gmail.com) - Regular User
- **John** (John@john.com) - Regular User  
- **Adam** (adam@adam.com) - Regular User
- **Admin** (admin@auth.com) - **Administrator** with category deletion privileges

### Categories
- Travel
- Auto Loan
- Student Loan

### Sample Expenses
- New York Business Trip - $1,500
- Ford Mustang Payment - $450
- Grand Canyon Trip - $800

## 👑 Admin Features

### Admin User Access
The application includes a special admin user with enhanced privileges:

- **Email:** `admin@auth.com`
- **Name:** `Admin`
- **Privileges:** Can delete categories system-wide

### Admin Identification
- **Visual Badge:** Admin users see a green "ADMIN" badge next to their username
- **Enhanced UI:** Admin users see delete buttons for categories
- **Permission Controls:** Backend validates admin status before allowing destructive operations

### Category Management Privileges

#### For Regular Users:
- ✅ View all categories
- ✅ Add new categories
- ✅ Use categories in expenses
- ❌ **Cannot delete categories** (shows "Admin only" text)

#### For Admin Users:
- ✅ All regular user permissions
- ✅ **Delete any category** with confirmation dialogs
- ✅ Visual delete buttons on category items
- ✅ System-wide category management

### Testing Admin Features

1. **Login as Admin:**
   ```
   Email: admin@auth.com
   Name: Admin
   ```

2. **Verify Admin Status:**
   - Look for green "ADMIN" badge in header
   - Category section shows red "Delete" buttons
   - Can successfully delete categories

3. **Compare with Regular User:**
   - Login with any other user (e.g., Siamak/Codeengine11@gmail.com)
   - No admin badge visible
   - Categories show "Admin only" text instead of delete buttons

## 🎨 Design Features

- **Clean Modern UI** - Professional appearance with intuitive design
- **Responsive Design** - Works perfectly on desktop, tablet, and mobile devices
- **Color-Coded Elements** - Easy visual distinction between different UI components
- **Form Validation** - Prevents invalid data entry with real-time feedback
- **Real-time Updates** - Immediate feedback on all user actions
- **Intuitive Navigation** - User-friendly interface with clear visual hierarchy
- **Admin Visual Indicators** - Green badges and enhanced UI for admin users
- **Permission-Based UI** - Interface adapts based on user privileges
- **Confirmation Dialogs** - Safe deletion with user confirmation for destructive actions
- **Status Messages** - Clear success/error messaging for all operations

## 🔧 Development Notes

### Architecture
- **Layered Architecture:** Controller → Service → Repository → Model
- **REST API:** All backend operations exposed as REST endpoints
- **OOP Principles:** Inheritance, Encapsulation, Polymorphism, Abstraction
- **Clean Code:** Well-documented and organized with clear separation of concerns
- **Security:** Role-based access control with admin privilege system
- **Permission Validation:** Double-layer security (frontend UI + backend validation)

### Database Design
- **JPA Entities** with proper relationships and constraints
- **Inheritance:** Transaction → Expense (demonstrating OOP inheritance)
- **Foreign Keys:** User-Expense, Category-Expense relationships
- **Constraints:** Email uniqueness, category name uniqueness
- **Admin System:** User table includes `is_admin` boolean field for privilege control
- **Data Integrity:** Proper cascading and referential integrity

### Security Implementation
- **Frontend Security:** UI elements hidden/shown based on user privileges
- **Backend Validation:** All destructive operations validate user permissions
- **Admin Identification:** Visual badges and clear role identification
- **Error Handling:** Graceful handling of unauthorized access attempts
- **Confirmation Systems:** Multi-step confirmation for critical operations

### Key Design Patterns
- **Repository Pattern:** Data access abstraction
- **Service Layer Pattern:** Business logic separation
- **MVC Pattern:** Model-View-Controller architecture
- **Dependency Injection:** Spring framework IoC container
- **RESTful Design:** Stateless API with proper HTTP methods

## 🔐 Admin System Implementation Details

### Backend Implementation

#### User Model Enhancement
```java
@Entity
@Table(name = "app_user")
public class User {
    // ...existing fields...
    
    @Column(name = "is_admin", nullable = false)
    private boolean admin = false;
    
    // Constructors, getters, setters...
}
```

#### CategoryService Permission Method
```java
public boolean deleteCategoryWithPermission(Long id, String userEmail) {
    // Find user by email
    User user = userRepository.findByEmail(userEmail);
    
    if (user == null) {
        throw new RuntimeException("User not found");
    }
    
    // Check if user is admin
    if (!user.isAdmin()) {
        return false; // Permission denied
    }
    
    deleteCategory(id);
    return true;
}
```

#### CategoryController Admin Endpoint
```java
@DeleteMapping("/categories/{id}")
Map<String,Object> deleteCategory(@PathVariable Long id, @RequestParam String userEmail) {
    Map<String,Object> response = new HashMap<>();
    try {
        boolean deleted = categoryService.deleteCategoryWithPermission(id, userEmail);
        response.put("deleted", deleted);
        response.put("message", deleted ? 
            "Category deleted successfully" : 
            "Access denied. Only admins can delete categories.");
        return response;
    } catch (Exception e) {
        response.put("deleted", false);
        response.put("message", "Error deleting category: " + e.getMessage());
        return response;
    }
}
```

### Frontend Implementation

#### Admin Badge Display
```javascript
// Show admin badge if user is admin
const adminBadge = currentUser.admin ? ' <span class="admin-badge">ADMIN</span>' : '';
userName.innerHTML = `Welcome, ${currentUser.name}${adminBadge}`;
```

#### Permission-Based UI Rendering
```javascript
function displayCategories() {
    container.innerHTML = categories.map(category => `
        <div class="category-item">
            <div class="category-name">${category.name}</div>
            <div class="category-actions">
                ${currentUser.admin ? 
                    `<button class="delete-category-btn" onclick="deleteCategory(${category.id})">Delete</button>` : 
                    `<span style="color: #666;">Admin only</span>`
                }
            </div>
        </div>
    `).join('');
}
```

#### Secure Category Deletion
```javascript
async function deleteCategory(categoryId) {
    if (!currentUser.admin) {
        showMessage('Only administrators can delete categories.', 'error');
        return;
    }
    
    if (!confirm('Are you sure you want to delete this category?')) {
        return;
    }
    
    const response = await fetch(`${API_BASE}/categories/${categoryId}?userEmail=${encodeURIComponent(currentUser.email)}`, {
        method: 'DELETE'
    });
    
    const result = await response.json();
    // Handle response...
}
```

### Database Schema
```sql
-- User table with admin privileges
CREATE TABLE app_user (
    id BIGINT NOT NULL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    is_admin BOOLEAN NOT NULL DEFAULT FALSE,
    name VARCHAR(255) NOT NULL
);

-- Sample admin user
INSERT INTO app_user (id, name, email, is_admin) 
VALUES (4, 'Admin', 'admin@auth.com', true);
```

## 🚀 Future Enhancements

Potential improvements you could add:

### Security & Authentication
- JWT-based authentication with refresh tokens
- Password encryption with bcrypt
- OAuth2 integration (Google, GitHub login)
- Two-factor authentication (2FA)
- Session timeout and security policies

### User Management
- User profile management and settings
- Multiple admin roles (Super Admin, Category Admin, etc.)
- User groups and organization management
- Activity logging and audit trails

### Expense Features
- Expense categories per user/organization
- Advanced date range filtering and search
- Recurring expense templates
- Expense approval workflows
- Bulk expense import/export

### Analytics & Reporting
- Interactive expense charts and graphs
- Monthly/yearly spending reports
- Category-wise spending analysis
- Budget tracking and alerts
- Export to CSV/PDF/Excel formats

### Advanced Features
- Multi-currency support with real-time exchange rates
- Receipt photo upload and OCR processing
- Expense budgets and spending limits
- Email notifications and reminders
- Mobile app (React Native/Flutter)
- Offline support with data synchronization

### System Improvements
- Database migration to PostgreSQL/MySQL
- Redis caching for performance
- Containerization with Docker
- CI/CD pipeline setup
- Comprehensive test coverage
- API rate limiting and monitoring

---

**Happy Expense Tracking! 💰📊**
