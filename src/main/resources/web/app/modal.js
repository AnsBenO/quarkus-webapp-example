/**
 * Close the modal
 * @param {Event} event
 */
export const closeModal = (event) => {
	if (event?.preventDefault) event.preventDefault();

	const backdrop = document.getElementById("modal-backdrop");
	const content = document.getElementById("modal-content");
	const modalContainer = document.getElementById("modal-div");

	if (backdrop && content) {
		backdrop.classList.remove("animate-fade-in");
		content.classList.remove("animate-scale-in");

		backdrop.classList.add("animate-fade-out");
		content.classList.add("animate-scale-out");

		backdrop.addEventListener(
			"animationend",
			() => {
				if (modalContainer) modalContainer.innerHTML = "";
			},
			{ once: true }
		);
	} else {
		if (modalContainer) modalContainer.innerHTML = "";
	}
};

/**
 * Load confirmation modal for deletion
 * @param {Event} event
 * @param {string|number} id
 */
export const handleDeleteItem = (event, id) => {
	if (event?.preventDefault) event.preventDefault();

	const modalContainer = document.getElementById("modal-div");
	if (modalContainer) {
		modalContainer.innerHTML = `
			${document.getElementById("confirm-dialog-template").innerHTML}
		`;
	}
};
