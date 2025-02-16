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
        uploadImage();
    });

    // Function to show error messages in the card-header
    function showError(message) {
        $(".card-header p").html(message).css("color", "red");
    }
});
