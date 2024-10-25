// See https://kit.svelte.dev/docs/types#app
// for information about these interfaces
declare global {
	namespace App {
		// interface Error {}
		interface Locals {
			user?: {
				id: string,
				onboarded: bool
			}
		}
		// interface PageData {}
		// interface PageState {}
		// interface Platform {}
	}
}

export { };
