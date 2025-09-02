import { closeModal, handleDeleteItem } from "./modal";
import { confirmDelete, setDeleteTargetId } from "./delete";
import { showToastNotification } from "./notifications";

document.addEventListener("DOMContentLoaded", () => {
	const todoContent = document.getElementById("todo-content");

	// Close modal after htmx content is loaded
	todoContent?.addEventListener("htmx:afterSettle", (event) => {
		closeModal(event);
	});

	// Expose globally for inline handlers
	window.closeModal = closeModal;
	window.handleDeleteItem = (event, id) => {
		setDeleteTargetId(id);
		handleDeleteItem(event, id);
	};
	window.confirmDelete = confirmDelete;
	window.showToastNotification = showToastNotification;
});
