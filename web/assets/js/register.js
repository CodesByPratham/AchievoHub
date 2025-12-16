$(document).ready(function () {
    const messageDiv = $(".text-white.mb-3.mb-md-0");

    // Function to validate email format
    function validateEmail(email) {
        const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
        return emailPattern.test(email);
    }

    // Function to validate contact number format
    function validateContactNumber(contact) {
        const contactPattern = /^[0-9]{10}$/;
        return contactPattern.test(contact);
    }

    // Function to validate strong password
    function validateStrongPassword(password) {
        const passwordPattern = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$/;
        return passwordPattern.test(password);
    }

    // Function to validate name format
    function validateName(name) {
        const namePattern = /^[a-zA-Z]+$/;
        return namePattern.test(name);
    }

    // Password validation script
    $('#password, #confirmPassword').on('input', function () {
        const password = $('#password').val().trim();
        const confirmPassword = $('#confirmPassword').val().trim();
        const feedbackElement = $('#passwordFeedback');

        if (!password || !confirmPassword) {
            feedbackElement.text("Password fields cannot be empty.");
            return;
        }

        if (password === confirmPassword) {
            feedbackElement.text("Passwords match.");
            feedbackElement.removeClass("text-danger").addClass("text-success");
        } else {
            feedbackElement.text("Passwords do not match.");
            feedbackElement.removeClass("text-success").addClass("text-danger");
        }
    });

    // Username validation script
    $('#username').on('input', function () {
        const username = $(this).val().trim();
        const feedbackElement = $('#usernameFeedback');

        if (username.length === 0) {
            feedbackElement.text('Username cannot be empty.');
            feedbackElement.removeClass('text-success').addClass('text-danger');
            return;
        }

        $.ajax({
            url: 'CheckUsername',
            method: 'POST',
            data: {username: username},
            success: function (response) {
                if (response === 'exists') {
                    feedbackElement.text('Username already exists. Please choose another.');
                    feedbackElement.removeClass('text-success').addClass('text-danger');
                } else if (response === 'available') {
                    feedbackElement.text('Username is available.');
                    feedbackElement.removeClass('text-danger').addClass('text-success');
                }
            }
        });
    });

    // Email validation script
    $('#email').on('input', function () {
        const email = $(this).val().trim();
        const feedbackElement = $('#emailFeedback');

        if (email.length === 0) {
            feedbackElement.text('Email cannot be empty.');
            feedbackElement.removeClass('text-success').addClass('text-danger');
            return;
        }

        if (!validateEmail(email)) {
            feedbackElement.text('Invalid email format.');
            feedbackElement.removeClass('text-success').addClass('text-danger');
            return;
        }

        $.ajax({
            url: 'CheckEmail',
            method: 'POST',
            data: {email: email},
            success: function (response) {
                if (response === 'exists') {
                    feedbackElement.text('Email is already registered.');
                    feedbackElement.removeClass('text-success').addClass('text-danger');
                } else if (response === 'available') {
                    feedbackElement.text('Email is available.');
                    feedbackElement.removeClass('text-danger').addClass('text-success');
                } else {
                    feedbackElement.text('Error checking email. Please try again.');
                    feedbackElement.removeClass('text-success').addClass('text-danger');
                }
            }
        });
    });

    $('form').on('submit', function (event) {
        const email = $('input[name="email"]').val().trim();
        const contact = $('input[name="contact"]').val().trim();
        const firstName = $('input[name="fname"]').val().trim();
        const lastName = $('input[name="lname"]').val().trim();
        const password = $('#password').val().trim();

        messageDiv.empty(); // Clear previous messages

        let validationPassed = true;

        if (!validateEmail(email)) {
            messageDiv.append("<p style='color: white;'>Please enter a valid email address.</p>");
            validationPassed = false;
        }

        if (!validateContactNumber(contact)) {
            messageDiv.append("<p style='color: white;'>Please enter a valid 10-digit contact number.</p>");
            validationPassed = false;
        }

        if (!validateName(firstName) || !validateName(lastName)) {
            messageDiv.append("<p style='color: white;'>First name and Last name should contain only alphabetic characters.</p>");
            validationPassed = false;
        }

        if (!validateStrongPassword(password)) {
            messageDiv.append("<p style='color: white;'>Password must contain at least 8 characters, including an uppercase letter, a lowercase letter, a digit, and a special character.</p>");
            validationPassed = false;
        }

        if (!validationPassed) {
            event.preventDefault(); // Prevent form submission if validation fails
        }
    });
});