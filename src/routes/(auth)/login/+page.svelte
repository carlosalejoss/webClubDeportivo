<script lang="ts">
	import Navbar from '$lib/components/navbar.svelte';
	import { CircleX } from 'lucide-svelte';
	import type { ActionData } from './$types';
	export let form: ActionData;
</script>

<Navbar />

<div class="hero min-h-screen bg-base-200">
	<div class="hero-content flex-col 2xl:w-full 2xl:flex-row-reverse">
		<div class="ml-8 text-center 2xl:text-left">
			<h1 class="w-fit max-w-2xl text-5xl font-bold">Accede a tu cuenta</h1>
			<p class="max-w-md py-6 2xl:max-w-full">
				Utiliza tu credenciales para acceder a la plataforma.
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
					<label class="label" for="DNI">
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
					<div class="mt-1">
						<a href="#/forgotten" class="link-hover link label-text-alt text-secondary"
							>¿Has olvidado la contraseña?</a
						>
					</div>
				</div>
				<div class="form-control mt-6">
					<button type="submit" class="btn btn-secondary">Acceder</button>
				</div>
				<div class="mt-2 flex flex-col items-center">
					<p>
						¿No tienes cuenta?
						<a href="/signup" class="text-secondary">Registrate</a>
					</p>
				</div>
			</form>
		</div>
	</div>
</div>
