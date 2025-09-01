// src/main/resources/web/app/app.js
import htmx from "htmx.org";
import swal from "sweetalert2";

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
			showToastNotification(
				"Deleted!",
				"Your item has been deleted.",
				"success"
			);
		}
	};

	/**
	 * Shows a notification using SweetAlert2.
	 * @function showToastNotification
	 * @param {string} title - The title of the notification.
	 * @param {string} message - The message content of the notification.
	 * @param {"success"|"error"|"warning"|"info"|"question"} type - The type of notification.
	 */
	const showToastNotification = (title, message, type) => {
		swal.fire({
			title: title,
			text: message,
			icon: type,
			toast: true,
			position: "bottom-end",
			showConfirmButton: false,
			timer: 3000,
			timerProgressBar: true,
			background: "#0a0f19",
			color: "#d1d5b9",
		});
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

	// Close modal after htmx content is loaded
	document
		.getElementById("todo-content")
		.addEventListener("htmx:afterSettle", function (event) {
			closeModal(event);
		});

	// Expose functions to global scope for inline event handlers
	window.closeModal = closeModal;
	window.deleteTargetId = deleteTargetId;
	window.handleDeleteItem = handleDeleteItem;
	window.showToastNotification = showToastNotification;
	window.confirmDelete = confirmDelete;
});
