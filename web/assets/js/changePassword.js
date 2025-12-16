$(document).ready(function () {
    function validateStrongPassword(password) {
        const passwordPattern = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$/;
        return passwordPattern.test(password);
    }

    function checkPasswordMatch() {
        let newPassword = $("#newPassword").val();
        let confirmPassword = $("#confirmPassword").val();
        let submitBtn = $("#submitBtn");

        if (!validateStrongPassword(newPassword)) {
            $("#passwordStrength").text("Password must have at least 8 characters, including an uppercase letter, a lowercase letter, a digit, and a special character.");
            submitBtn.prop("disabled", true);
        } else {
            $("#passwordStrength").text("");
        }

        if (newPassword !== confirmPassword) {
            $("#passwordMatch").text("Passwords do not match.");
            submitBtn.prop("disabled", true);
        } else {
            $("#passwordMatch").text("");
            if (validateStrongPassword(newPassword)) {
                submitBtn.prop("disabled", false);
            }
        }
    }

    $("#newPassword, #confirmPassword").on("input", checkPasswordMatch);
});