// Clear all error messages
function clearErrors() {
    const errors = document.getElementsByClassName('error');
    for (let error of errors) {
        error.innerText = '';
    }
}

// Display error message for a specific element
function displayError(elementId, message) {
    document.getElementById(elementId).innerText = message;
}


function validateUsername(username) {
    const usernameRegex = /^[a-zA-Z]+(?: [a-zA-Z]+)*$/;
    if (!usernameRegex.test(username)) {
        return 'Username must be alphabetic.';
    }
    return '';
}


function validatePassword(password) {
    const passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\W)(?!.*\s).{6,}$/;
    if (!passwordRegex.test(password)) {
        return 'Password must be at least 6 characters, contain 1 digit, 1 uppercase, 1 lowercase, 1 special character, and no spaces.';
    }
    return '';
}


function validateConfirmPassword(password, confirmPassword) {
    if (password !== confirmPassword) {
        return 'Passwords do not match';
    }
    return '';
}


function validatePhone(phone) {
    const phoneRegex = /^\d{10}$/;
    if (!phoneRegex.test(phone)) {
        return 'Phone number must be 10 digits.';
    }
    return '';
}


function validateAge(age) {
    if (age < 1 || age > 120) {
        return 'Please enter a valid age (1-120).';
    }
    return '';
}


function validateEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        return 'Invalid email format.';
    }
    return '';
}


function validateAddress(address) {
    if (address.trim() === '') {
        return 'Address cannot be empty.';
    }
    return '';
}
