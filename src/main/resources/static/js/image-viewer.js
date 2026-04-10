document.addEventListener("DOMContentLoaded", () => {
    const modal = document.createElement("div");
    modal.className = "img-modal";

    const modalImg = document.createElement("img");
    modal.appendChild(modalImg);

    document.body.appendChild(modal);

    document.querySelectorAll("img").forEach(img => {
        img.classList.add("demo-img");

        img.addEventListener("click", () => {
            modalImg.src = img.src;
            modal.classList.add("active");
        });
    });

    modal.addEventListener("click", () => {
        modal.classList.remove("active");
    });

    document.addEventListener("keydown", (e) => {
        if (e.key === "Escape") {
            modal.classList.remove("active");
        }
    });
});