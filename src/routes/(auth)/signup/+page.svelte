<script lang="ts">
	import Navbar from '$lib/components/navbar.svelte';
	import { CircleX } from 'lucide-svelte';
	import type { ActionData } from '../login/$types';
	import { goto } from '$app/navigation';
	import { onMount } from 'svelte';
	import type { PageData, PageServerLoad } from './$types';

	export let form: ActionData;
	export let data: PageData;

	onMount(() => {
		if (form?.error === null) {
			setInterval(() => {
				goto('/');
			}, 100);
		}
	});
</script>

<Navbar account={data.user} />

<div class="hero min-h-screen bg-base-200">
	<div class="hero-content flex-col 2xl:w-full 2xl:flex-row-reverse">
		<div class="ml-8 text-center 2xl:text-left">
			<h1 class="w-fit max-w-2xl text-5xl font-bold">Registrate en SCA</h1>
			<p class="max-w-md py-6 2xl:max-w-full">
				Por favor, introduzque sus datos para registrase en la plataforma.
			</p>
		</div>
		<div class="card w-full max-w-sm shrink-0 bg-base-100 shadow-2xl">
			<form class="card-body" method="POST">
				{#if form?.error}
					<div class="card flex flex-row items-center gap-3 bg-rose-700 px-6 py-4 font-bold">
						<CircleX />
						{form.error}
					</div>
				{/if}
				<div class="form-control">
					<label class="label" for="nombre">
						<span class="label-text">Nombre completo</span>
					</label>
					<input
						type="text"
						name="nombre"
						placeholder="Nombre"
						class="input input-bordered"
						required
					/>
				</div>
				<div class="form-control">
					<label class="label" for="dni">
						<span class="label-text">DNI</span>
					</label>
					<input type="text" name="dni" placeholder="DNI" class="input input-bordered" required />
				</div>
				<div class="form-control">
					<label class="label" for="contraseña">
						<span class="label-text">Contraseña</span>
					</label>
					<input
						type="password"
						name="password"
						placeholder="Contraseña"
						class="input input-bordered"
						required
					/>
				</div>
				<div class="form-control mt-6">
					<button class="btn btn-secondary">Registrarse</button>
				</div>
				<div class="mt-2 flex flex-col items-center">
					<p>
						¿Ya tienes cuenta?
						<a href="/login" class="text-secondary">Accede</a>
					</p>
				</div>
			</form>
		</div>
	</div>
</div>
