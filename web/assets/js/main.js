document.addEventListener("DOMContentLoaded", function () {
    let lastScrollTop = 0;
    const navbar = document.querySelector(".navbar");

    if (!navbar) {
        return;
    }

    // Ensure navbar is visible on page load
    navbar.style.top = "0";

    window.addEventListener("scroll", function () {
        let currentScroll = window.scrollY;

        if (currentScroll === 0 || currentScroll < lastScrollTop) {
            // If at the top OR scrolling up → Show navbar
            navbar.style.top = "0";
        } else {
            // Scrolling down → Hide navbar
            navbar.style.top = "-70px";
        }

        lastScrollTop = currentScroll;
    });
});
