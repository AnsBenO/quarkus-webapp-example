import htmx from "htmx.org";
import { closeModal } from "./modal";

let deleteTargetId = null;

/**
 * Set the current delete target ID
 */
export const setDeleteTargetId = (id) => {
	deleteTargetId = id;
};

/**
 * Confirm deletion and make htmx request
 */
export const confirmDelete = (event) => {
	if (event?.preventDefault) event.preventDefault();
	const btn = event.currentTarget;
	const btnText = btn.querySelector(".btn-text");
	const spinner = btn.querySelector("#buttonLoader");

	btnText.classList.add("invisible");
	spinner.classList.remove("invisible");

	if (deleteTargetId) {
		htmx.ajax("DELETE", `item/${deleteTargetId}`, {
			target: "#todo-content",
			swap: "innerHTML",
		}).then(() => {
			spinner.classList.add("invisible");
			btnText.classList.remove("invisible");
			closeModal(event);
		});
	}
};
