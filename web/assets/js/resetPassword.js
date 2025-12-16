$(document).ready(function () {
    function validateStrongPassword(password) {
        const passwordPattern = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$/;
        return passwordPattern.test(password);
    }

    function checkPasswordMatch() {
        let password = $("#password").val();
        let confirmPassword = $("#confirmPassword").val();
        let submitBtn = $("#submitBtn");

        if (!validateStrongPassword(password)) {
            $("#passwordStrength").text("Password must contain at least 8 characters, including an uppercase letter, a lowercase letter, a digit, and a special character.");
            submitBtn.prop("disabled", true);
        } else {
            $("#passwordStrength").text("");
        }

        if (password !== confirmPassword) {
            $("#passwordMatch").text("Passwords do not match.");
            submitBtn.prop("disabled", true);
        } else {
            $("#passwordMatch").text("");
            if (validateStrongPassword(password)) {
                submitBtn.prop("disabled", false);
            }
        }
    }

    $("#password, #confirmPassword").on("input", checkPasswordMatch);
});