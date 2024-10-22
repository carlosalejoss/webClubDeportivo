<script lang="ts">
	import { Volleyball } from 'lucide-svelte';
</script>

<div class="navbar bg-base-100 px-6">
	<div class="flex-1">
		<a href="/" class="btn btn-ghost text-xl">
			<Volleyball />
			Sports Center Aragon
		</a>
	</div>
	<div class="flex-none">
		<ul class="menu menu-horizontal px-1 text-lg">
			<li><a>Reservar</a></li>
			<li><a>Clases</a></li>
			<!-- <li>
				<details>
					<summary>Parent</summary>
					<ul class="bg-base-100 rounded-t-none p-2">
						<li><a>Link 1</a></li>
						<li><a>Link 2</a></li>
					</ul>
				</details>
			</li> -->
		</ul>
		<a href="/login" class="btn btn-secondary ml-3 text-lg">Iniciar Sesión</a>
	</div>
</div>
