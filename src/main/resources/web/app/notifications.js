import swal from "sweetalert2";

/**
 * Shows a notification using SweetAlert2.
 * @param {string} title
 * @param {string} message
 * @param {"success"|"error"|"warning"|"info"|"question"} type
 */
export const showToastNotification = (title, message, type) => {
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
