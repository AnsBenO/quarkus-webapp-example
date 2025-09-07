import { closeModal, handleDeleteItem } from "./js/modal";
import { confirmDelete, setDeleteTargetId } from "./js/delete";
import { showToastNotification } from "./js/notifications";

document.addEventListener("DOMContentLoaded", () => {
	const todoContent = document.getElementById("todo-content");

	// Close modal after htmx content is loaded
	todoContent?.addEventListener("htmx:afterSettle", (event) => {
		closeModal(event);
	});

	// Show/hide loader
	document.body.addEventListener("htmx:beforeRequest", function (evt) {
		document.getElementById("itemLoader").classList.remove("hidden");
	});

	document.body.addEventListener("htmx:afterRequest", function (evt) {
		document.getElementById("itemLoader").classList.add("hidden");
	});

	// Expose globally for inline handlers
	window.closeModal = closeModal;

	/**
	 * Set the current delete target ID and open the confirmation modal.
	 * @param {Event} event - The event which triggered the deletion
	 * @param {number} id - The ID of the item to be deleted
	 */
	window.handleDeleteItem = (event, id) => {
		setDeleteTargetId(id);
		handleDeleteItem(event, id);
	};

	window.confirmDelete = confirmDelete;
	window.showToastNotification = showToastNotification;
});
