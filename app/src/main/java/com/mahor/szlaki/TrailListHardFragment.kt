package com.mahor.szlaki

class TrailListHardFragment : TrailListFragment() {
    override fun observeTrails(adapter: TrailListAdapter) {
        trailViewModel.hardTrails.observe(viewLifecycleOwner) { trails ->
            trails.let { adapter.submitList(it)}
        }
    }
}