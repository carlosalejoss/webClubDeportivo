<script lang="ts">
	import Navbar from '$lib/components/navbar.svelte';
</script>

<Navbar />

<div class="hero bg-base-200 min-h-screen">
	<div class="hero-content flex-col 2xl:w-full 2xl:flex-row-reverse">
		<div class="ml-8 text-center 2xl:text-left">
			<h1 class="w-fit max-w-2xl text-5xl font-bold">Registrate en SCA</h1>
			<p class="max-w-md py-6 2xl:max-w-full">
				Por favor, introduzque sus datos para registrase en la plataforma.
			</p>
		</div>
		<div class="card bg-base-100 w-full max-w-sm shrink-0 shadow-2xl">
			<form class="card-body">
				<div class="form-control">
					<label class="label" for="DNI">
						<span class="label-text">DNI</span>
					</label>
					<input type="text" placeholder="DNI" class="input input-bordered" required />
				</div>
				<div class="form-control">
					<label class="label" for="contraseña">
						<span class="label-text">Contraseña</span>
					</label>
					<input type="password" placeholder="Contraseña" class="input input-bordered" required />
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
