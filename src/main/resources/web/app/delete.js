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

	if (deleteTargetId) {
		htmx.ajax("DELETE", `/todolist/item/${deleteTargetId}`, {
			target: "#todo-content",
			swap: "innerHTML",
		});

		closeModal(event);
	}
};
