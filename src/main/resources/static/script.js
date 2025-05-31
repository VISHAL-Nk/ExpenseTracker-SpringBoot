// Global variables
let currentUser = null;
let categories = [];
let expenses = [];

// API Base URL
const API_BASE = 'http://localhost:8080/api';

// DOM Elements
const authSection = document.getElementById('auth-section');
const appSection = document.getElementById('app-section');
const userInfo = document.getElementById('user-info');
const userName = document.getElementById('user-name');
const messageDiv = document.getElementById('message');

// Initialize app
document.addEventListener('DOMContentLoaded', function() {
    // Check if user is logged in (from localStorage)
    const savedUser = localStorage.getItem('currentUser');
    if (savedUser) {
        currentUser = JSON.parse(savedUser);
        showApp();
    }
    
    // Set today's date as default
    document.getElementById('expense-date').value = new Date().toISOString().split('T')[0];
});

// Auth functions
function showLogin() {
    document.getElementById('login-form').style.display = 'block';
    document.getElementById('register-form').style.display = 'none';
    document.getElementById('login-tab').classList.add('active');
    document.getElementById('register-tab').classList.remove('active');
}

function showRegister() {
    document.getElementById('login-form').style.display = 'none';
    document.getElementById('register-form').style.display = 'block';
    document.getElementById('login-tab').classList.remove('active');
    document.getElementById('register-tab').classList.add('active');
}

async function register(event) {
    event.preventDefault();
    
    const name = document.getElementById('register-name').value;
    const email = document.getElementById('register-email').value;
    
    try {
        const response = await fetch(`${API_BASE}/auth/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ name, email })
        });
        
        const data = await response.json();
        
        if (response.ok) {
            showMessage('Registration successful! Please login.', 'success');
            showLogin();
            // Clear form
            document.getElementById('register-name').value = '';
            document.getElementById('register-email').value = '';
        } else {
            showMessage(data.message || 'Registration failed', 'error');
        }
    } catch (error) {
        showMessage('Network error. Please try again.', 'error');
        console.error('Registration error:', error);
    }
}

async function login(event) {
    event.preventDefault();
    
    const email = document.getElementById('login-email').value;
    const name = document.getElementById('login-name').value;
    
    try {
        const response = await fetch(`${API_BASE}/auth/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email, name })
        });
        
        const data = await response.json();
        
        if (response.ok) {
            currentUser = data.user;
            localStorage.setItem('currentUser', JSON.stringify(currentUser));
            showMessage('Login successful!', 'success');
            showApp();
        } else {
            showMessage(data.message || 'Login failed', 'error');
        }
    } catch (error) {
        showMessage('Network error. Please try again.', 'error');
        console.error('Login error:', error);
    }
}

function logout() {
    currentUser = null;
    localStorage.removeItem('currentUser');
    authSection.style.display = 'block';
    appSection.style.display = 'none';
    userInfo.style.display = 'none';
    
    // Clear forms
    document.getElementById('login-email').value = '';
    document.getElementById('login-name').value = '';
    showMessage('Logged out successfully', 'success');
}

// App functions
async function showApp() {
    authSection.style.display = 'none';
    appSection.style.display = 'block';
    userInfo.style.display = 'flex';
    
    // Show admin badge if user is admin
    const adminBadge = currentUser.admin ? '<span class="admin-badge">ADMIN</span>' : '';
    userName.innerHTML = `Welcome, ${currentUser.name}${adminBadge}`;
    
    await loadCategories();
    await loadExpenses();
}

async function loadCategories() {
    try {
        const response = await fetch(`${API_BASE}/categories`);
        if (response.ok) {
            categories = await response.json();
            populateCategoryDropdown();
            displayCategories();
        }
    } catch (error) {
        console.error('Error loading categories:', error);
    }
}

function populateCategoryDropdown() {
    const select = document.getElementById('expense-category');
    select.innerHTML = '<option value="">Select Category</option>';
    
    categories.forEach(category => {
        const option = document.createElement('option');
        option.value = category.id;
        option.textContent = category.name;
        select.appendChild(option);
    });
}

async function addCategory(event) {
    event.preventDefault();
    
    const name = document.getElementById('category-name').value;
    
    try {
        const response = await fetch(`${API_BASE}/categories`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ name })
        });
        
        if (response.ok) {
            showMessage('Category added successfully!', 'success');
            document.getElementById('category-name').value = '';
            await loadCategories();
        } else {
            const error = await response.json();
            showMessage(error.message || 'Failed to add category', 'error');
        }
    } catch (error) {
        showMessage('Network error. Please try again.', 'error');
        console.error('Add category error:', error);
    }
}

async function addExpense(event) {
    event.preventDefault();
    
    const description = document.getElementById('expense-description').value;
    const amount = document.getElementById('expense-amount').value;
    const date = document.getElementById('expense-date').value;
    const location = document.getElementById('expense-location').value;
    const categoryId = document.getElementById('expense-category').value;
    
    try {
        const formData = new URLSearchParams();
        formData.append('description', description);
        formData.append('amount', amount);
        formData.append('date', date);
        formData.append('location', location);
        formData.append('userId', currentUser.id);
        formData.append('categoryId', categoryId);
        
        const response = await fetch(`${API_BASE}/expenses/create`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: formData
        });
        
        if (response.ok) {
            showMessage('Expense added successfully!', 'success');
            
            // Clear form
            document.getElementById('expense-description').value = '';
            document.getElementById('expense-amount').value = '';
            document.getElementById('expense-location').value = '';
            document.getElementById('expense-category').value = '';
            
            await loadExpenses();
        } else {
            showMessage('Failed to add expense', 'error');
        }
    } catch (error) {
        showMessage('Network error. Please try again.', 'error');
        console.error('Add expense error:', error);
    }
}

async function loadExpenses() {
    try {
        const response = await fetch(`${API_BASE}/expenses/user/${currentUser.id}`);
        if (response.ok) {
            expenses = await response.json();
            displayExpenses();
        }
    } catch (error) {
        console.error('Error loading expenses:', error);
    }
}

function displayExpenses() {
    const container = document.getElementById('expenses-container');
    const totalSpan = document.getElementById('total-expenses');
    
    if (expenses.length === 0) {
        container.innerHTML = '<p style="text-align: center; color: #666;">No expenses yet. Add your first expense above!</p>';
        totalSpan.textContent = 'Total: $0.00';
        return;
    }
    
    // Calculate total
    const total = expenses.reduce((sum, expense) => sum + expense.amount, 0);
    totalSpan.textContent = `Total: $${total.toFixed(2)}`;
    
    // Display expenses
    container.innerHTML = expenses.map(expense => `
        <div class="expense-item">
            <div class="expense-info">
                <div class="expense-description">${expense.description}</div>
                <div class="expense-details">
                    ${expense.category.name} • ${expense.date} • ${expense.location || 'No location'}
                </div>
            </div>
            <div style="display: flex; align-items: center;">
                <div class="expense-amount">$${expense.amount.toFixed(2)}</div>
                <button class="delete-btn" onclick="deleteExpense(${expense.id})">×</button>
            </div>
        </div>
    `).join('');
}

async function deleteExpense(expenseId) {
    if (!confirm('Are you sure you want to delete this expense?')) {
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE}/expenses/${expenseId}`, {
            method: 'DELETE'
        });
        
        if (response.ok) {
            showMessage('Expense deleted successfully!', 'success');
            await loadExpenses();
        } else {
            showMessage('Failed to delete expense', 'error');
        }
    } catch (error) {
        showMessage('Network error. Please try again.', 'error');
        console.error('Delete expense error:', error);
    }
}

// Category Management Functions
function displayCategories() {
    const container = document.getElementById('categories-container');
    
    if (categories.length === 0) {
        container.innerHTML = '<div class="no-categories">No categories available</div>';
        return;
    }
    
    container.innerHTML = categories.map(category => `
        <div class="category-item">
            <div class="category-name">${category.name}</div>
            <div class="category-actions">
                ${currentUser.admin ? 
                    `<button class="delete-category-btn" onclick="deleteCategory(${category.id})">Delete</button>` : 
                    `<span style="color: #666; font-size: 0.9rem;">Admin only</span>`
                }
            </div>
        </div>
    `).join('');
}

async function deleteCategory(categoryId) {
    if (!currentUser.admin) {
        showMessage('Only administrators can delete categories.', 'error');
        return;
    }
    
    if (!confirm('Are you sure you want to delete this category? This action cannot be undone.')) {
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE}/categories/${categoryId}?userEmail=${encodeURIComponent(currentUser.email)}`, {
            method: 'DELETE'
        });
        
        const result = await response.json();
        
        if (response.ok && result.deleted) {
            showMessage(result.message || 'Category deleted successfully!', 'success');
            await loadCategories();
        } else {
            showMessage(result.message || 'Failed to delete category', 'error');
        }
    } catch (error) {
        showMessage('Network error. Please try again.', 'error');
        console.error('Delete category error:', error);
    }
}

// Utility functions
function showMessage(text, type) {
    messageDiv.textContent = text;
    messageDiv.className = `message ${type}`;
    messageDiv.classList.add('show');
    
    setTimeout(() => {
        messageDiv.classList.remove('show');
    }, 3000);
}
