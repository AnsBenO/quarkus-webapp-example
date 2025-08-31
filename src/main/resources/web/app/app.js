// src/main/resources/web/app/app.js
import htmx from "htmx.org";

document.addEventListener("DOMContentLoaded", function () {
	let deleteTargetId = null;

	/**
	 * Handles the deletion request by opening a custom confirmation modal.
	 */
	const handleDeleteItem = (event, id) => {
		if (event && typeof event.preventDefault === "function") {
			event.preventDefault();
		}

		deleteTargetId = id;

		// Load the confirmation modal into #modal-div
		document.getElementById("modal-div").innerHTML = `
		${document.getElementById("confirm-dialog-template").innerHTML}
	`;
	};

	/**
	 * Confirms deletion and performs htmx request.
	 */
	const confirmDelete = (event) => {
		if (event && typeof event.preventDefault === "function") {
			event.preventDefault();
		}

		if (deleteTargetId) {
			htmx.ajax("DELETE", `/todolist/item/${deleteTargetId}`, {
				target: "#todo-content",
				swap: "innerHTML",
			});

			// Close modal after deletion
			closeModal(event);

			// Optional: show a success toast/notification
			showNotification(
				"Deleted!",
				"Your item has been deleted.",
				"success"
			);
		}
	};

	/**
	 * Close the modal
	 * @function closeModal
	 * @param {Event} event The event that is triggered when the modal is closed.
	 */
	const closeModal = (event) => {
		if (event && typeof event.preventDefault === "function") {
			event.preventDefault();
		}

		const backdrop = document.getElementById("modal-backdrop");
		const content = document.getElementById("modal-content");
		const modalContainer = document.getElementById("modal-div");

		if (backdrop && content) {
			// Add fade-out + scale-out classes
			backdrop.classList.remove("animate-fade-in");
			content.classList.remove("animate-scale-in");

			backdrop.classList.add("animate-fade-out");
			content.classList.add("animate-scale-out");

			// Wait for animation to finish, then remove
			backdrop.addEventListener(
				"animationend",
				() => {
					if (modalContainer) {
						modalContainer.innerHTML = "";
					}
				},
				{ once: true }
			);
		} else {
			// fallback (in case animations fail)
			modalContainer.innerHTML = "";
		}
	};
	window.closeModal = closeModal;
	window.deleteTargetId = deleteTargetId;
	window.handleDeleteItem = handleDeleteItem;
	window.confirmDelete = confirmDelete;
});

// Close modal after htmx content is loaded
document
	.getElementById("todo-content")
	.addEventListener("htmx:afterSettle", function (event) {
		closeModal(event);
	});
