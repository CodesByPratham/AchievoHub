// Drag and drop functionality
$(document).ready(function () {

    // Date Picker for Duration
    const picker = new Litepicker({
        element: document.getElementById('litepicker'),
        format: 'MMM-YYYY',
        singleMode: false,
        allowRepick: true
    });

    const dropArea = $("#drop-area");
    const inputFile = $("#input-file");
    const imagePreviewDiv = $("#image-view");

    uploadImage();

    // Functionality for drag and drop
    function uploadImage() {
        const previousImage = document.getElementById("previousImage").value;
        const files = inputFile[0].files;
        const imgLink = (typeof files[0] === 'undefined') ? previousImage : URL.createObjectURL(files[0]);

        if (imgLink) {
            imagePreviewDiv.css({
                'background': 'transparent',
                'background-image': `url(${imgLink})`,
                'height': '100%',
                'width': '100%',
                'background-size': 'contain',
                'background-repeat': 'no-repeat',
                'background-position': 'center',
                'border': '0'
            }).text('');
        }
    }

    // Handle file input change
    inputFile.on("change", function () {
        validateImage();
        uploadImage();
    });

    // Handle dragover event to allow drop
    dropArea.on("dragover", function (e) {
        e.preventDefault();
    });

    // Handle drop event
    dropArea.on("drop", function (e) {
        e.preventDefault();
        const files = e.originalEvent.dataTransfer.files;
        inputFile[0].files = files;
        validateImage();
        uploadImage();
    });

    // Function to validate image file
    function validateImage() {
        const file = inputFile[0].files[0];
        if (file) {
            const validTypes = ['image/jpeg', 'image/png', 'image/gif'];
            if (!validTypes.includes(file.type)) {
                showError("Only JPG, PNG, and GIF files are allowed.");
                inputFile.val('');
                return false;
            }

            if (file.size > 2 * 1024 * 1024) {
                showError("File size must be less than 2MB.");
                inputFile.val('');
                return false;
            }
        }
        return true;
    }

    // Form validation on submission
    $("form").submit(function (e) {
        let isValid = true;
        let errorMessage = "";

        // Title validation
        const title = $("input[name='title']").val().trim();
        if (!title.match(/^[A-Za-z0-9 ]{1,100}$/)) {
            errorMessage += "Invalid Title.<br>";
            isValid = false;
        }

        // Organization validation
        const organization = $("input[name='organization']").val().trim();
        if (!organization.match(/^[A-Za-z0-9 ]{1,100}$/)) {
            errorMessage += "Invalid Organization Name.<br>";
            isValid = false;
        }

        // Organization Website validation
        const orgUrl = $("input[name='orgUrl']").val().trim();
        if (!orgUrl.match(/^(https?:\/\/)?(www\.)?[A-Za-z0-9.-]+\.[A-Za-z]{2,6}\/?.*$/)) {
            errorMessage += "Invalid Organization Website URL.<br>";
            isValid = false;
        }

        // Duration validation
        const duration = $("input[name='duration']").val().trim();
        if (duration === "") {
            errorMessage += "Duration is required.<br>";
            isValid = false;
        }

        // Date Achieved validation (cannot be in the future)
        const dateAchieved = $("input[name='dateAchieved']").val();
        if (dateAchieved) {
            const achievedDate = new Date(dateAchieved);
            const today = new Date();
            if (achievedDate > today) {
                errorMessage += "Date Achieved cannot be in the future.<br>";
                isValid = false;
            }
        } else {
            errorMessage += "Date Achieved is required.<br>";
            isValid = false;
        }

        // Category, Type, and Method validation (must be selected)
        if (!$("select[name='category']").val()) {
            errorMessage += "Please select a Category.<br>";
            isValid = false;
        }
        if (!$("select[name='type']").val()) {
            errorMessage += "Please select a Type.<br>";
            isValid = false;
        }
        if (!$("select[name='method']").val()) {
            errorMessage += "Please select a Method.<br>";
            isValid = false;
        }

        // Description validation
        const description = $("textarea[name='description']").val().trim();
        if (description.length < 10 || description.length > 500) {
            errorMessage += "Description must be between 10 and 500 characters.<br>";
            isValid = false;
        }

        // Validate image file if a new one is uploaded
        if (!validateImage()) {
            isValid = false;
        }

        // Display validation errors
        if (!isValid) {
            showError(errorMessage);
            e.preventDefault();
        }
    });

    // Function to show error messages in the card-header
    function showError(message) {
        $(".card-header p").html(message).css("color", "red");
    }
});
