/* 
 Created on : 28-Nov-2024, 5:25:06 pm
 Author     : PRATHAM
 */

// Rage of date picker
const picker = new Litepicker({
    element: document.getElementById('litepicker'),
    format: 'MMM-YYYY',
    singleMode: false,
    allowRepick: true
});

// Drag and drop functionality
$(document).ready(function () {
    const dropArea = $("#drop-area");
    const inputFile = $("#input-file");
    const imagePreviewDiv = $("#add-image-view");

    // Functionality for drag and drop
    function uploadImage() {
        const files = inputFile[0].files; // Get file from input element
        if (files && files[0]) {
            const imgLink = URL.createObjectURL(files[0]); // Generate URL for the selected file
            imagePreviewDiv.css({
                'background': 'transparent',
                'background-image': `url(${imgLink})`,
                'height': '100%',
                'width': '100%',
                'background-size': 'contain',
                'background-repeat': 'no-repeat',
                'background-position': 'center',
                'border': '0'
            }).text(''); // Clear any text inside the div
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
        const files = e.originalEvent.dataTransfer.files; // Access dropped files
        inputFile[0].files = files; // Set dropped files to the input element
        uploadImage();
    });
});
